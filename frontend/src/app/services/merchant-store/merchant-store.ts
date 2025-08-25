import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MerchantStore } from '../../models/merchant-store';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class MerchantStoreService {
  private readonly baseURL = 'http://localhost:8081/api/locations';

  constructor(private readonly http: HttpClient) {}

  createMerchantStore(merchantStore: MerchantStore): Observable<ResponseEntityData<MerchantStore>> {
    return this.http.post<ResponseEntityData<MerchantStore>>(this.baseURL, merchantStore);
  }

  getAllMerchantStores(): Observable<ResponseEntityData<MerchantStore[]>> {
    return this.http.get<ResponseEntityData<MerchantStore[]>>(this.baseURL);
  }

  getMerchantStoreById(id: number): Observable<ResponseEntityData<MerchantStore> | MerchantStore | MerchantStore[]> {
    return this.http.get<ResponseEntityData<MerchantStore> | MerchantStore | MerchantStore[]>(`${this.baseURL}/${id}`);
  }

  updateMerchantStore(id: number, merchantStore: MerchantStore): Observable<ResponseEntityData<MerchantStore>> {
    return this.http.put<ResponseEntityData<MerchantStore>>(`${this.baseURL}/${id}`, merchantStore);
  }

  deleteMerchantStore(id: number): Observable<ResponseEntityData<null>> {
    return this.http.delete<ResponseEntityData<null>>(`${this.baseURL}/${id}`);
  }
}