import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Acquirer } from '../../models/Acquirer';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class AcquirerService {
  private readonly baseURL = 'http://localhost:8081/api/acquirer-id-configs';

  constructor(private readonly http: HttpClient) {}

  createAcquirer(acquirer: Acquirer): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, acquirer);
  }

  getAllAcquirers(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllAcquirersIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  getAcquirerById(id: number): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/${id}`);
  }

  updateAcquirer(id: number, acquirer: Acquirer): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, acquirer);
  }

  deleteAcquirer(id: number): Observable<ResponseEntityData> {
    return this.http.delete<ResponseEntityData>(`${this.baseURL}/${id}`);
  }
}
