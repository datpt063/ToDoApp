import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServerTestModule } from '../../../test.module';
import { TasksDetailComponent } from 'app/entities/tasks/tasks-detail.component';
import { Tasks } from 'app/shared/model/tasks.model';

describe('Component Tests', () => {
  describe('Tasks Management Detail Component', () => {
    let comp: TasksDetailComponent;
    let fixture: ComponentFixture<TasksDetailComponent>;
    const route = ({ data: of({ tasks: new Tasks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ServerTestModule],
        declarations: [TasksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TasksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TasksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tasks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tasks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
