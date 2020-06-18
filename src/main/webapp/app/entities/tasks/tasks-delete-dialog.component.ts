import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITasks } from 'app/shared/model/tasks.model';
import { TasksService } from './tasks.service';

@Component({
  templateUrl: './tasks-delete-dialog.component.html'
})
export class TasksDeleteDialogComponent {
  tasks?: ITasks;

  constructor(protected tasksService: TasksService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tasksService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tasksListModification');
      this.activeModal.close();
    });
  }
}
