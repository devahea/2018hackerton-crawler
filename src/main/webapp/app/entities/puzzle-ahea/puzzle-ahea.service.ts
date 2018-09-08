import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPuzzleAhea } from 'app/shared/model/puzzle-ahea.model';

type EntityResponseType = HttpResponse<IPuzzleAhea>;
type EntityArrayResponseType = HttpResponse<IPuzzleAhea[]>;

@Injectable({ providedIn: 'root' })
export class PuzzleAheaService {
    private resourceUrl = SERVER_API_URL + 'api/puzzles';

    constructor(private http: HttpClient) {}

    create(puzzle: IPuzzleAhea): Observable<EntityResponseType> {
        return this.http.post<IPuzzleAhea>(this.resourceUrl, puzzle, { observe: 'response' });
    }

    update(puzzle: IPuzzleAhea): Observable<EntityResponseType> {
        return this.http.put<IPuzzleAhea>(this.resourceUrl, puzzle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPuzzleAhea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPuzzleAhea[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
