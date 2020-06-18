import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITasks, Tasks } from 'app/shared/model/tasks.model';
import { TasksService } from './tasks.service';
import { TasksComponent } from './tasks.component';
import { TasksDetailComponent } from './tasks-detail.component';
import { TasksUpdateComponent } from './tasks-update.component';

@Injectable({ providedIn: 'root' })
export class TasksResolve implements Resolve<ITasks> {
  constructor(private service: TasksService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITasks> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tasks: HttpResponse<Tasks>) => {
          if (tasks.body) {
            return of(tasks.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tasks());
  }
}

export const tasksRoute: Routes = [
  {
    path: '',
    component: TasksComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Tasks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TasksDetailComponent,
    resolve: {
      tasks: TasksResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Tasks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TasksUpdateComponent,
    resolve: {
      tasks: TasksResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Tasks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TasksUpdateComponent,
    resolve: {
      tasks: TasksResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Tasks'
    },
    canActivate: [UserRouteAccessService]
  }
];
