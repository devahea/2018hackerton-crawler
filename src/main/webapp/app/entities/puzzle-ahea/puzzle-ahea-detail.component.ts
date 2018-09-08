import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuzzleAhea } from 'app/shared/model/puzzle-ahea.model';

@Component({
    selector: 'jhi-puzzle-ahea-detail',
    templateUrl: './puzzle-ahea-detail.component.html'
})
export class PuzzleAheaDetailComponent implements OnInit {
    puzzle: IPuzzleAhea;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ puzzle }) => {
            this.puzzle = puzzle;
        });
    }

    previousState() {
        window.history.back();
    }
}
