import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServerSharedModule } from 'app/shared/shared.module';
import { TasksComponent } from './tasks.component';
import { TasksDetailComponent } from './tasks-detail.component';
import { TasksUpdateComponent } from './tasks-update.component';
import { TasksDeleteDialogComponent } from './tasks-delete-dialog.component';
import { tasksRoute } from './tasks.route';

@NgModule({
  imports: [ServerSharedModule, RouterModule.forChild(tasksRoute)],
  declarations: [TasksComponent, TasksDetailComponent, TasksUpdateComponent, TasksDeleteDialogComponent],
  entryComponents: [TasksDeleteDialogComponent]
})
export class ServerTasksModule {}
