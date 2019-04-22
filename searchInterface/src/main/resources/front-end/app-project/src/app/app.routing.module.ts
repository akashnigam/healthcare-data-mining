import { NgModule } from '@angular/core';
import { RouterModule,Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { SymptomsComponent } from './symptoms/symptoms.component';
import { DiseasesComponent } from './diseases/diseases.component';

const routes: Routes=[
    {path:'',component:DiseasesComponent},
    {path:'diseases',component:DiseasesComponent},
    {path:'symptoms',component: SymptomsComponent}
   

]
@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule],
  
})
export class AppRoutingModule{

}