import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AheaCrawlerPuzzleAheaModule } from './puzzle-ahea/puzzle-ahea.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AheaCrawlerPuzzleAheaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AheaCrawlerEntityModule {}
