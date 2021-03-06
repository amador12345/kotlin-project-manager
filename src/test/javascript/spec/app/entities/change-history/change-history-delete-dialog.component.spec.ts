/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PmAppTestModule } from '../../../test.module';
import { ChangeHistoryDeleteDialogComponent } from 'app/entities/change-history/change-history-delete-dialog.component';
import { ChangeHistoryService } from 'app/entities/change-history/change-history.service';

describe('Component Tests', () => {
  describe('ChangeHistory Management Delete Component', () => {
    let comp: ChangeHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<ChangeHistoryDeleteDialogComponent>;
    let service: ChangeHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PmAppTestModule],
        declarations: [ChangeHistoryDeleteDialogComponent]
      })
        .overrideTemplate(ChangeHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChangeHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChangeHistoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
