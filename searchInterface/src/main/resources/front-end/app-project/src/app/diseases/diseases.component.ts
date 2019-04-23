import { Component ,OnInit } from '@angular/core';
import { FormControl } from "@angular/forms";
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { HttpClient,HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-diseases',
  templateUrl: './diseases.component.html',
  styleUrls: ['./diseases.component.css']
})
export class DiseasesComponent implements OnInit {

  flag=false;
  title = 'materialApp';
  myControl = new FormControl();
  disease:any=null;

  flagdemo=false;
  headers=new HttpHeaders({ 'Access-Control-Allow-Origin':'*'});

  stateCtrl = new FormControl();
  filteredStates: Observable<any>;

  states=null;

  constructor( private http: HttpClient) {

   setTimeout(()=>{
    this.filteredStates = this.stateCtrl.valueChanges
           .pipe(
             startWith(''),
             map(state => state ? this._filterStates(state) : this.states.slice() )
           );
   },4000);




   }
 
   private _filterStates(value: string): any[] {
     const filterValue = value.toLowerCase();
 
     return this.states.filter(state => state.toLowerCase().indexOf(filterValue) === 0);
   }

    displayFn(user): string | undefined {
       console.log(user);
       console.log(typeof(user));
       return user ? user : undefined;
   }

 ngOnInit(){

   this.http.get<any>('http://localhost:8080/diseaseNames',{headers:this.headers})
   .subscribe( response =>{
       this.states=response;
       console.log(this.states);
   })


 }

 submitValue(value:any)
 {
   console.log(value);

    this.http.get<any>('http://localhost:8080/getDisease/'+value,{headers:this.headers})
                                  .subscribe( response =>{


                                  response.diseaseSymptoms.sort((a, b) => (a.count < b.count) ? 1 : -1);
                                  console.log(response);

                                      response.diseaseSymptoms=response.diseaseSymptoms.slice(0,5);
                                      this.disease = response;
                                      this.flag=true;
                                      console.log(this.disease.diseaseSymptoms);});


 }




}
