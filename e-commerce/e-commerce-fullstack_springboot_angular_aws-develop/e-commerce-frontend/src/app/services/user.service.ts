import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  listAll() {
    return this.http.get<any[]>(`${this.baseUrl}/all`);
  }

  setActive(userId: number, active: boolean) {
    return this.http.put(`${this.baseUrl}/${userId}/active`, null, { params: { active } });
  }

  delete(userId: number) {
    return this.http.delete(`${this.baseUrl}/${userId}`);
  }

  uploadAvatar(userId: number, file: File) {
    const form = new FormData();
    form.append('image', file);
    // Backend currently accepts profileImage via updateUserDetails; implementing true upload endpoint can follow
    return this.http.put(`${this.baseUrl}/${userId}`, { profileImage: file.name });
  }
}


