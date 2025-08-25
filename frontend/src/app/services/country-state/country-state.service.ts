import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CountryState } from '../../models/CountryState';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class CountryStateService {
  private readonly baseURL = 'http://localhost:8081/api/country-states';

  constructor(private readonly http: HttpClient) {}

  createCountryState(state: CountryState): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, state);
  }

  getAllCountryStates(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllCountryStatesIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  updateCountryState(id: number, state: CountryState): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, state);
  }
}
