/*
  Typescript Document that defines the functionality
  of the Survey List componenet. 
*/
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-surveylist',
  templateUrl: './surveylist.component.html',
  styleUrls: ['./surveylist.component.css']
})
export class SurveylistComponent implements OnInit {

  // LOCAL URL
  url: String = "http://localhost:8080"

  // DEPLOYED URL
  // url: String = ""

  productList: any

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
    let resp = this.http.get(this.url + '/studentsurvey-api/restresources')
    resp.subscribe(
      (response) => {
        let responseString = JSON.stringify(response);
        this.productList = JSON.parse(responseString);
      },
      (error) => {
        alert("We had some trouble loading the page.\n Please try again")
        this.router.navigateByUrl('/')
      }
    )
    
  }

  onClickBack(): void {
    this.router.navigateByUrl('/')
  }

}
