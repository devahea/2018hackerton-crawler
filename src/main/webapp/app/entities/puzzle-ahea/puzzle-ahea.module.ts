import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AheaCrawlerSharedModule } from 'app/shared';
import {
    PuzzleAheaComponent,
    PuzzleAheaDetailComponent,
    PuzzleAheaUpdateComponent,
    PuzzleAheaDeletePopupComponent,
    PuzzleAheaDeleteDialogComponent,
    puzzleRoute,
    puzzlePopupRoute
} from './';

const ENTITY_STATES = [...puzzleRoute, ...puzzlePopupRoute];

@NgModule({
    imports: [AheaCrawlerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PuzzleAheaComponent,
        PuzzleAheaDetailComponent,
        PuzzleAheaUpdateComponent,
        PuzzleAheaDeleteDialogComponent,
        PuzzleAheaDeletePopupComponent
    ],
    entryComponents: [PuzzleAheaComponent, PuzzleAheaUpdateComponent, PuzzleAheaDeleteDialogComponent, PuzzleAheaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AheaCrawlerPuzzleAheaModule {}
