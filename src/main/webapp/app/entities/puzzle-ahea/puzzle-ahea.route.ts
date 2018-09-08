import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PuzzleAhea } from 'app/shared/model/puzzle-ahea.model';
import { PuzzleAheaService } from './puzzle-ahea.service';
import { PuzzleAheaComponent } from './puzzle-ahea.component';
import { PuzzleAheaDetailComponent } from './puzzle-ahea-detail.component';
import { PuzzleAheaUpdateComponent } from './puzzle-ahea-update.component';
import { PuzzleAheaDeletePopupComponent } from './puzzle-ahea-delete-dialog.component';
import { IPuzzleAhea } from 'app/shared/model/puzzle-ahea.model';

@Injectable({ providedIn: 'root' })
export class PuzzleAheaResolve implements Resolve<IPuzzleAhea> {
    constructor(private service: PuzzleAheaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((puzzle: HttpResponse<PuzzleAhea>) => puzzle.body));
        }
        return of(new PuzzleAhea());
    }
}

export const puzzleRoute: Routes = [
    {
        path: 'puzzle-ahea',
        component: PuzzleAheaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aheaCrawlerApp.puzzle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'puzzle-ahea/:id/view',
        component: PuzzleAheaDetailComponent,
        resolve: {
            puzzle: PuzzleAheaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aheaCrawlerApp.puzzle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'puzzle-ahea/new',
        component: PuzzleAheaUpdateComponent,
        resolve: {
            puzzle: PuzzleAheaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aheaCrawlerApp.puzzle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'puzzle-ahea/:id/edit',
        component: PuzzleAheaUpdateComponent,
        resolve: {
            puzzle: PuzzleAheaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aheaCrawlerApp.puzzle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const puzzlePopupRoute: Routes = [
    {
        path: 'puzzle-ahea/:id/delete',
        component: PuzzleAheaDeletePopupComponent,
        resolve: {
            puzzle: PuzzleAheaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'aheaCrawlerApp.puzzle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
