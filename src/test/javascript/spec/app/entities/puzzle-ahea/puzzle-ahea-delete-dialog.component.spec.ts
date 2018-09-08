/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AheaCrawlerTestModule } from '../../../test.module';
import { PuzzleAheaDeleteDialogComponent } from 'app/entities/puzzle-ahea/puzzle-ahea-delete-dialog.component';
import { PuzzleAheaService } from 'app/entities/puzzle-ahea/puzzle-ahea.service';

describe('Component Tests', () => {
    describe('PuzzleAhea Management Delete Component', () => {
        let comp: PuzzleAheaDeleteDialogComponent;
        let fixture: ComponentFixture<PuzzleAheaDeleteDialogComponent>;
        let service: PuzzleAheaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AheaCrawlerTestModule],
                declarations: [PuzzleAheaDeleteDialogComponent]
            })
                .overrideTemplate(PuzzleAheaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PuzzleAheaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PuzzleAheaService);
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
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
