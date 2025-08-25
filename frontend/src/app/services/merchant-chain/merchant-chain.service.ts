import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseEntityData } from '../../models/ResponseEntityData';
import { Merchant } from 'src/app/models/Merchant';

@Injectable({
  providedIn: 'root'
})
export class MerchantChainService {

  private readonly baseURL = 'http://localhost:8081/api/merchants';

  constructor(private readonly http: HttpClient) {}

  createMerchant(merchant: Merchant): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, merchant);
  }

  getMerchantById(id: number): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/${id}`);
  }

  getAllMerchants(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllMerchantIdAndCode(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  updateMerchant(id: number, merchant: Merchant): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, merchant);
  }

  deleteMerchant(id: number): Observable<ResponseEntityData> {
    return this.http.delete<ResponseEntityData>(`${this.baseURL}/${id}`);
  }

  getAllMerchantCategoryCodes(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/category-codes`);
  }

}
