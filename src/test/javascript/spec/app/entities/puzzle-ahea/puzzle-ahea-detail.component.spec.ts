/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AheaCrawlerTestModule } from '../../../test.module';
import { PuzzleAheaDetailComponent } from 'app/entities/puzzle-ahea/puzzle-ahea-detail.component';
import { PuzzleAhea } from 'app/shared/model/puzzle-ahea.model';

describe('Component Tests', () => {
    describe('PuzzleAhea Management Detail Component', () => {
        let comp: PuzzleAheaDetailComponent;
        let fixture: ComponentFixture<PuzzleAheaDetailComponent>;
        const route = ({ data: of({ puzzle: new PuzzleAhea(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AheaCrawlerTestModule],
                declarations: [PuzzleAheaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PuzzleAheaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PuzzleAheaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.puzzle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
