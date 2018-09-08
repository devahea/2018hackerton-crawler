/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AheaCrawlerTestModule } from '../../../test.module';
import { PuzzleAheaComponent } from 'app/entities/puzzle-ahea/puzzle-ahea.component';
import { PuzzleAheaService } from 'app/entities/puzzle-ahea/puzzle-ahea.service';
import { PuzzleAhea } from 'app/shared/model/puzzle-ahea.model';

describe('Component Tests', () => {
    describe('PuzzleAhea Management Component', () => {
        let comp: PuzzleAheaComponent;
        let fixture: ComponentFixture<PuzzleAheaComponent>;
        let service: PuzzleAheaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AheaCrawlerTestModule],
                declarations: [PuzzleAheaComponent],
                providers: []
            })
                .overrideTemplate(PuzzleAheaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PuzzleAheaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PuzzleAheaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PuzzleAhea(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.puzzles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
