import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TasksService } from 'app/entities/tasks/tasks.service';
import { ITasks, Tasks } from 'app/shared/model/tasks.model';

describe('Service Tests', () => {
  describe('Tasks Service', () => {
    let injector: TestBed;
    let service: TasksService;
    let httpMock: HttpTestingController;
    let elemDefault: ITasks;
    let expectedResult: ITasks | ITasks[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TasksService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Tasks(0, 0, currentDate, currentDate, false, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            crearedDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Tasks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            crearedDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            crearedDate: currentDate,
            dueDate: currentDate
          },
          returnedFromService
        );

        service.create(new Tasks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Tasks', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            crearedDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            impotance: true,
            status: 1,
            noiDung: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            crearedDate: currentDate,
            dueDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Tasks', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            crearedDate: currentDate.format(DATE_TIME_FORMAT),
            dueDate: currentDate.format(DATE_TIME_FORMAT),
            impotance: true,
            status: 1,
            noiDung: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            crearedDate: currentDate,
            dueDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Tasks', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
