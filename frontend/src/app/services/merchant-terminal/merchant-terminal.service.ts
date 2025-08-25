import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { MerchantTerminal } from 'src/app/models/merchant-terminal';

@Injectable({
  providedIn: 'root'
})
export class MerchantTerminalService {
//   private apiUrl = 'http://example.com/api/terminals'; // Replace with your API endpoint
    private apiUrl = 'http://localhost:8081/api/terminals'; 

  constructor(private http: HttpClient) {}

  getAllMerchantTerminals(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  getMerchantTerminalById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createMerchantTerminal(terminal: MerchantTerminal): Observable<any> {
    return this.http.post<any>(this.apiUrl, terminal);
  }

  updateMerchantTerminal(terminal: MerchantTerminal): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${terminal.id}`, terminal);
  }

  deleteMerchantTerminal(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}