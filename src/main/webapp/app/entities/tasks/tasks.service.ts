import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITasks } from 'app/shared/model/tasks.model';

type EntityResponseType = HttpResponse<ITasks>;
type EntityArrayResponseType = HttpResponse<ITasks[]>;

@Injectable({ providedIn: 'root' })
export class TasksService {
  public resourceUrl = SERVER_API_URL + 'api/tasks';

  constructor(protected http: HttpClient) {}

  create(tasks: ITasks): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tasks);
    return this.http
      .post<ITasks>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tasks: ITasks): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tasks);
    return this.http
      .put<ITasks>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITasks>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITasks[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tasks: ITasks): ITasks {
    const copy: ITasks = Object.assign({}, tasks, {
      crearedDate: tasks.crearedDate && tasks.crearedDate.isValid() ? tasks.crearedDate.toJSON() : undefined,
      dueDate: tasks.dueDate && tasks.dueDate.isValid() ? tasks.dueDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.crearedDate = res.body.crearedDate ? moment(res.body.crearedDate) : undefined;
      res.body.dueDate = res.body.dueDate ? moment(res.body.dueDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tasks: ITasks) => {
        tasks.crearedDate = tasks.crearedDate ? moment(tasks.crearedDate) : undefined;
        tasks.dueDate = tasks.dueDate ? moment(tasks.dueDate) : undefined;
      });
    }
    return res;
  }
}
