import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseEntityData } from '../../models/ResponseEntityData';
import { MerchantInstitution } from '../../models/MerchantInstitution';

@Injectable({
  providedIn: 'root'
})
export class MerchantGroupService {

  private readonly baseURL = 'http://localhost:8081/api/merchant-institutions';

  constructor(private readonly http: HttpClient) { }

  createMerchantInstitution(merchantInstitution: MerchantInstitution): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, merchantInstitution);
  }

  getMerchantInstitutionById(id: number): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/${id}`);
  } 

  getAllMerchantInstitutions(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllMerchantInstitutionsIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  updateMerchantInstitution(id: number, merchantInstitution: MerchantInstitution): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, merchantInstitution);
  }

  deleteMerchantInstitution(id: number): Observable<ResponseEntityData> {
    return this.http.delete<ResponseEntityData>(`${this.baseURL}/${id}`);
  }   
  
}
