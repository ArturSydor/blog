import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PostPayload} from "./add-post/post-payload";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AddPostService {
  private baseUrl = 'http://localhost:8080/api/posts';

  constructor(private httpClient: HttpClient) { }

  addPost(postPayload: PostPayload): Observable<any> {
    return this.httpClient.post(this.baseUrl, postPayload);
  }
}
