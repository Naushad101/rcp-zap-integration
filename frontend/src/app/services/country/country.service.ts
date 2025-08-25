import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Country } from '../../models/Country';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private readonly baseURL = 'http://localhost:8081/api/countries';

  constructor(private readonly http: HttpClient) {}

  createCountry(country: Country): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, country);
  }

  getAllCountries(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllCountriesIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  updateCountry(id: number, country: Country): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, country);
  }
}
