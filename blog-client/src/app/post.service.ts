import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PostPayload} from "./add-post/post-payload";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private baseUrl = 'http://localhost:8080/api/posts';

  constructor(private httpClient: HttpClient) { }

  addPost(postPayload: PostPayload): Observable<any> {
    return this.httpClient.post(this.baseUrl, postPayload);
  }

  getAll(): Observable<Array<PostPayload>> {
    return this.httpClient.get<Array<PostPayload>>(this.baseUrl);
  }

  getOne(postId: Number): Observable<PostPayload> {
    return this.httpClient.get<PostPayload>(this.baseUrl + '/' + postId);
  }
}
