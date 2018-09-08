export interface IPuzzleAhea {
    id?: number;
    size?: number;
    source?: string;
}

export class PuzzleAhea implements IPuzzleAhea {
    constructor(public id?: number, public size?: number, public source?: string) {}
}
