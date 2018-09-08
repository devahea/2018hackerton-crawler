import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPuzzleAhea } from 'app/shared/model/puzzle-ahea.model';
import { PuzzleAheaService } from './puzzle-ahea.service';

@Component({
    selector: 'jhi-puzzle-ahea-update',
    templateUrl: './puzzle-ahea-update.component.html'
})
export class PuzzleAheaUpdateComponent implements OnInit {
    private _puzzle: IPuzzleAhea;
    isSaving: boolean;

    constructor(private puzzleService: PuzzleAheaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ puzzle }) => {
            this.puzzle = puzzle;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.puzzle.id !== undefined) {
            this.subscribeToSaveResponse(this.puzzleService.update(this.puzzle));
        } else {
            this.subscribeToSaveResponse(this.puzzleService.create(this.puzzle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPuzzleAhea>>) {
        result.subscribe((res: HttpResponse<IPuzzleAhea>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get puzzle() {
        return this._puzzle;
    }

    set puzzle(puzzle: IPuzzleAhea) {
        this._puzzle = puzzle;
    }
}
