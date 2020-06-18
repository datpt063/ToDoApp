import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITasks } from 'app/shared/model/tasks.model';
import { TasksService } from './tasks.service';
import { TasksDeleteDialogComponent } from './tasks-delete-dialog.component';

@Component({
  selector: 'jhi-tasks',
  templateUrl: './tasks.component.html'
})
export class TasksComponent implements OnInit, OnDestroy {
  tasks?: ITasks[];
  eventSubscriber?: Subscription;

  constructor(protected tasksService: TasksService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tasksService.query().subscribe((res: HttpResponse<ITasks[]>) => (this.tasks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTasks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITasks): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTasks(): void {
    this.eventSubscriber = this.eventManager.subscribe('tasksListModification', () => this.loadAll());
  }

  delete(tasks: ITasks): void {
    const modalRef = this.modalService.open(TasksDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tasks = tasks;
  }
}
