export class CurrentUser {
  username: string;
  role: string;
  token: string;

  public constructor(username: string, role: string, token: string ){
    this.username = username;
    this.role = role;
    this.token = token;
  }
}
