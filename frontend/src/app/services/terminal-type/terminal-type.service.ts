import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TerminalType } from '../../models/TerminalTypes';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class TerminalTypeService {
  private readonly baseURL = 'http://localhost:8081/api/terminal-types';

  constructor(private readonly http: HttpClient) {}

  createTerminalType(terminalType: TerminalType): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, terminalType);
  }

  getAllTerminalTypes(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllTerminalTypesIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  getTerminalTypeById(id: number): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/${id}`);
  }

  updateTerminalType(id: number, terminalType: TerminalType): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, terminalType);
  }

  deleteTerminalType(id: number): Observable<ResponseEntityData> {
    return this.http.delete<ResponseEntityData>(`${this.baseURL}/${id}`);
  }
}