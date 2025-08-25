import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Currency } from '../../models/Currency';
import { ResponseEntityData } from '../../models/ResponseEntityData'; 

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private readonly baseURL = 'http://localhost:8081/api/currencies'; 

  constructor(private readonly http: HttpClient) {}

  createCurrency(currency: Currency): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, currency);
  }

  getAllCurrencies(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllCurrenciesIdAndCode(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  updateCurrency(id: number, currency: Currency): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, currency);
  }
}
