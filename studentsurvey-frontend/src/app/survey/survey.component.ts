/*
  Typescript class that defines the functionality of the 
  Survey component. 
*/
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'

@Component({
  selector: 'app-survey',
  templateUrl: './survey.component.html',
  styleUrls: ['./survey.component.css']
})

export class SurveyComponent implements OnInit {

  today: String = new Date().toDateString()
  // LOCAL URL
  url: String = "http://localhost:8080"

  // DEPLOYED URL
  // url: String = ""

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {}

  parseData(data: any): String[] {
    let checkedBoxes: String[] = []
    if (data.students) {
      checkedBoxes.push("Students")
    } 

    if (data.location) {
      checkedBoxes.push("Location")
    } 

    if (data.campus) {
      checkedBoxes.push("Campus")
    } 

    if (data.atmosphere) {
      checkedBoxes.push("Atmosphere")
    } 

    if (data.dorms) {
      checkedBoxes.push("Dorms")
    } 

    if (data.sports) {
      checkedBoxes.push("Sports")
    } 

    return checkedBoxes
  }

  onClickSubmit(data: any): void {
    if (
      !data.fname || 
      !data.lname || 
      !data.street || 
      !data.city ||
      !data.state || 
      !data.zip || 
      !data.pnumber || 
      !data.email) 
    {
      alert("Required fields are not filled")
    } else { 
      let checkedBoxes = this.parseData(data).toString()
      let body = new URLSearchParams()
      body.set('date', new Date().toLocaleDateString())
      body.set('fname', data.fname)
      body.set('lname', data.lname)
      body.set('street', data.street)
      body.set('city', data.city)
      body.set('zip', data.zip)
      body.set('pnumber', data.pnumber)
      body.set('email', data.email)
      body.set('checkboxes', checkedBoxes)
      body.set('choice', data.choice)
      body.set('recommendation', data.recommendation)
      
      let options = {
        headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
      }
      
      let resp = this.http.post(this.url + "/studentsurvey-api/restresources/newsurvey/", body, options)
      resp.subscribe(
        (response) => {
          alert("Survey Successfully Submitted!")
          this.router.navigateByUrl('/')
        },
        (error) => {
          alert("There was an error submitting. " + body.toString())
          console.log(error)
        }
      )
      
    }
  }

  onClickCancel(): void {
    this.router.navigateByUrl('/')
  }

}
