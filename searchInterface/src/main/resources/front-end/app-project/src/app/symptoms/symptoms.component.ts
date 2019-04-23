import { Component ,OnInit } from '@angular/core';
import { FormControl } from "@angular/forms";
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { HttpClient,HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-symptoms',
  templateUrl: './symptoms.component.html',
  styleUrls: ['./symptoms.component.css']
})
export class SymptomsComponent implements OnInit {

  flag=false;
  title = 'materialApp';
  myControl = new FormControl();
  disease:any=null;
  vsum:any=0;

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
   },1000);




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

   this.http.get<any>('http://localhost:8080/symptomNames',{headers:this.headers})
   .subscribe( response =>{
       this.states=response;
       console.log(this.states);
   })


 }

 submitValue(value:any)
 {
   console.log(value);

    this.http.get<any>('http://localhost:8080/getDiseasesSymptom/'+value,{headers:this.headers})
                                  .subscribe( response =>{
                                      this.disease=response;

                                     for (var i = 0; i < response.length; i++){
                                         var obj = response[i];
                                         for (var key in obj){
                                             if(key==='count')
                                             this.vsum += obj[key];
                                         }
                                     }

                                     this.disease.sort((a, b) => (a.count < b.count) ? 1 : -1);
                                      this.disease=this.disease.slice(0,5);
                                      this.flag=true;
                                      console.log(this.disease);});


 }


}
