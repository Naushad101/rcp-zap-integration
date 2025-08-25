import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TerminalSiting } from '../../models/TerminalSiting';
import { ResponseEntityData } from '../../models/ResponseEntityData';

@Injectable({
  providedIn: 'root'
})
export class TerminalSitingService {
  private readonly baseURL = 'http://localhost:8081/api/terminal-sitings';

  constructor(private readonly http: HttpClient) {}

  createTerminalSiting(terminalSiting: TerminalSiting): Observable<ResponseEntityData> {
    return this.http.post<ResponseEntityData>(this.baseURL, terminalSiting);
  }

  getAllTerminalSitings(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(this.baseURL);
  }

  getAllTerminalSitingsIdAndName(): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/filter`);
  }

  getTerminalSitingById(id: number): Observable<ResponseEntityData> {
    return this.http.get<ResponseEntityData>(`${this.baseURL}/${id}`);
  }

  updateTerminalSiting(id: number, terminalSiting: TerminalSiting): Observable<ResponseEntityData> {
    return this.http.put<ResponseEntityData>(`${this.baseURL}/${id}`, terminalSiting);
  }

  deleteTerminalSiting(id: number): Observable<ResponseEntityData> {
    return this.http.delete<ResponseEntityData>(`${this.baseURL}/${id}`);
  }
}