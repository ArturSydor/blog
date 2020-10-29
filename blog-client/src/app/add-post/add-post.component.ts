import { Component, OnInit } from '@angular/core';
import {FormControl, FormControlName, FormGroup} from "@angular/forms";
import {PostPayload} from "./post-payload";
import {AddPostService} from "../add-post.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {

  addPostForm: FormGroup;
  postPayLoad: PostPayload;

  constructor(private addPostService: AddPostService, private router: Router) {
    this.addPostForm = new FormGroup({
      title: new FormControl(''),
      content: new FormControl('')
    });
    this.postPayLoad = {
      id: '',
      title: '',
      content: ''
    }
  }

  ngOnInit(): void {
  }

  addPost() {
    this.postPayLoad.title = this.addPostForm.get('title').value;
    this.postPayLoad.content = this.addPostForm.get('content').value;
    this.addPostService.addPost(this.postPayLoad).subscribe(data => {
      console.log('created: ' + data);
      this.router.navigateByUrl('/');
    }, error => {
      console.log('failed to create post.' + error.message)
    })
  }
}
