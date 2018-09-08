import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuzzleAhea } from 'app/shared/model/puzzle-ahea.model';
import { PuzzleAheaService } from './puzzle-ahea.service';

@Component({
    selector: 'jhi-puzzle-ahea-delete-dialog',
    templateUrl: './puzzle-ahea-delete-dialog.component.html'
})
export class PuzzleAheaDeleteDialogComponent {
    puzzle: IPuzzleAhea;

    constructor(private puzzleService: PuzzleAheaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.puzzleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'puzzleListModification',
                content: 'Deleted an puzzle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-puzzle-ahea-delete-popup',
    template: ''
})
export class PuzzleAheaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ puzzle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PuzzleAheaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.puzzle = puzzle;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
