import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITasks, Tasks } from 'app/shared/model/tasks.model';
import { TasksService } from './tasks.service';

@Component({
  selector: 'jhi-tasks-update',
  templateUrl: './tasks-update.component.html'
})
export class TasksUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    crearedDate: [],
    dueDate: [],
    impotance: [],
    status: [],
    noiDung: []
  });

  constructor(protected tasksService: TasksService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tasks }) => {
      if (!tasks.id) {
        const today = moment().startOf('day');
        tasks.crearedDate = today;
        tasks.dueDate = today;
      }

      this.updateForm(tasks);
    });
  }

  updateForm(tasks: ITasks): void {
    this.editForm.patchValue({
      id: tasks.id,
      userId: tasks.userId,
      crearedDate: tasks.crearedDate ? tasks.crearedDate.format(DATE_TIME_FORMAT) : null,
      dueDate: tasks.dueDate ? tasks.dueDate.format(DATE_TIME_FORMAT) : null,
      impotance: tasks.impotance,
      status: tasks.status,
      noiDung: tasks.noiDung
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tasks = this.createFromForm();
    if (tasks.id !== undefined) {
      this.subscribeToSaveResponse(this.tasksService.update(tasks));
    } else {
      this.subscribeToSaveResponse(this.tasksService.create(tasks));
    }
  }

  private createFromForm(): ITasks {
    return {
      ...new Tasks(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      crearedDate: this.editForm.get(['crearedDate'])!.value
        ? moment(this.editForm.get(['crearedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dueDate: this.editForm.get(['dueDate'])!.value ? moment(this.editForm.get(['dueDate'])!.value, DATE_TIME_FORMAT) : undefined,
      impotance: this.editForm.get(['impotance'])!.value,
      status: this.editForm.get(['status'])!.value,
      noiDung: this.editForm.get(['noiDung'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITasks>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
