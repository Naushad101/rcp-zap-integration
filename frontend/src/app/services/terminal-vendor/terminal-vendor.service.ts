import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TerminalVendor } from '../../models/TerminalVendor';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class TerminalVendorService {
  private readonly baseURL = 'http://localhost:8081/api/terminal-vendors';

  constructor(private readonly http: HttpClient) {}

  createTerminalVendor(terminalVendor: TerminalVendor): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, terminalVendor);
  }

  getAllTerminalVendors(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllTerminalVendorsIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  getTerminalVendorById(id: number): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/${id}`);
  }

  updateTerminalVendor(id: number, terminalVendor: TerminalVendor): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, terminalVendor);
  }

  deleteTerminalVendor(id: number): Observable<ResponseEntityData> {
    return this.http.delete<ResponseEntityData>(`${this.baseURL}/${id}`);
  }
}