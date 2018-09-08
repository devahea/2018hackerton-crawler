/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AheaCrawlerTestModule } from '../../../test.module';
import { PuzzleAheaUpdateComponent } from 'app/entities/puzzle-ahea/puzzle-ahea-update.component';
import { PuzzleAheaService } from 'app/entities/puzzle-ahea/puzzle-ahea.service';
import { PuzzleAhea } from 'app/shared/model/puzzle-ahea.model';

describe('Component Tests', () => {
    describe('PuzzleAhea Management Update Component', () => {
        let comp: PuzzleAheaUpdateComponent;
        let fixture: ComponentFixture<PuzzleAheaUpdateComponent>;
        let service: PuzzleAheaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AheaCrawlerTestModule],
                declarations: [PuzzleAheaUpdateComponent]
            })
                .overrideTemplate(PuzzleAheaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PuzzleAheaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PuzzleAheaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PuzzleAhea(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.puzzle = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PuzzleAhea();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.puzzle = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
