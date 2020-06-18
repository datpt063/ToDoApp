import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ServerTestModule } from '../../../test.module';
import { TasksComponent } from 'app/entities/tasks/tasks.component';
import { TasksService } from 'app/entities/tasks/tasks.service';
import { Tasks } from 'app/shared/model/tasks.model';

describe('Component Tests', () => {
  describe('Tasks Management Component', () => {
    let comp: TasksComponent;
    let fixture: ComponentFixture<TasksComponent>;
    let service: TasksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ServerTestModule],
        declarations: [TasksComponent]
      })
        .overrideTemplate(TasksComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TasksComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TasksService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Tasks(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tasks && comp.tasks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
