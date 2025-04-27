## API Specification

### Authentication APIs

#### 1. Login
- **URL**: `POST /auth/login`
- **Description**: Đăng nhập vào hệ thống
- **Request Body**:
  ```json
  {
      "email": "admin",
      "password": "admin"
  }
  ```
- **Response**:
    - `200 OK`: Returns an authentication token.
      ```json
      {
         "code": 200,
         "message": "Login successfully",
         "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNeUFwcCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzM0MzY2NzA5LCJpYXQiOjE3MzQzNTk1MDksImp0aSI6IjYwMjVhNzJmLTdmYWQtNGYxYS04Y2FjLTllNjExOTkzMWM0NCIsInNjb3BlIjoiUk9MRV9BRE1JTiJ9.RhGhtazMVhvoFi952G_VnYB-hCd3nbqkEGE7u9wcNK8",
         "expiration": "2025-04-20 13:24"
      }
      ```
    - `401 Unauthorized`
      ```json
      {
         "code": 401,
         "message": "Unauthenticated"     
      }
      ```
    - `401 Invalid email`
      ```json
      {
         "code": 401,
         "message": "Please enter email ends with @gmail.com"     
      }
      ```
  - `401 Invalid login way`
    ```json
    {
       "code": 401,
       "message": "This email must be logan through google service"     
    }
    ```
    - `404 User not found`
      ```json
      {
         "code": 404,
         "message": "User not found"     
      }
      ```

#### 2. Register Patient
- **URL**: `POST /auth/register`
- **Description**: Bệnh nhân tạo tài khoản.
- **Request Body**:
  ```json
  {
     "email": "user1@gmail.com",
     "password": "123",
     "name": "Nguyen Cong Thanh",
     "birthDate": "2000-01-01",
     "phoneNumber": "0123456789",
     "address": "123 street ABC, District 1, TP.HCM",
     "assurance": "ABCDEF"
  }
  ```
- **Response**:
    - `200 OK`: Register successfully.
      ```json
      {
          "code": 200,
          "message": "Patient register successfully"
      }
      ```
    - `401 User existed`
      ```json
      {
         "code": 409,
         "message": "User already existed"     
      }
      ```
    - `401 Invalid email`
      ```json
      {
         "code": 401,
         "message": "Please enter email ends with @gmail.com"     
      }
      ```

#### 3. Login With Google
- **URL**: `GET /auth/login-google`
- **Description**: Đăng nhập vào hệ thống bằng tài khoản google
- **Response**:
    - `200 OK`: Returns an authentication token.
      ```json
      {
         "code": 200,
         "message": "Login successfully",
         "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNeUFwcCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzM0MzY2NzA5LCJpYXQiOjE3MzQzNTk1MDksImp0aSI6IjYwMjVhNzJmLTdmYWQtNGYxYS04Y2FjLTllNjExOTkzMWM0NCIsInNjb3BlIjoiUk9MRV9BRE1JTiJ9.RhGhtazMVhvoFi952G_VnYB-hCd3nbqkEGE7u9wcNK8",
         "expiration": "2025-04-20 13:24"
      }
      ```
    - `401 Unauthorized`
      ```json
      {
         "code": 401,
         "message": "Unauthenticated"     
      }
      ```
    - `404 User not found`
      ```json
      {
         "code": 404,
         "message": "User not found"     
      }
      ```

#### 3. Login With Google
- **URL**: `GET /auth/login-google`
- **Description**: Đăng nhập vào hệ thống bằng tài khoản google
- **Response**:
    - `200 OK`: Return a link.
      ```json
      {
          "code": 200,
          "message": "Url generated successful",
          "data": "https://accounts.google.com/o/oauth2/auth?client_id=1054955116253-ltvgl9ddhvgddbtdencqit1es8h95mhe.apps.googleusercontent.com&redirect_uri=http://localhost:8080/auth/callback-google&response_type=code&scope=email&state=login"
      }
      ```
    #### After access and login through google service
    - `200 OK`: Returns an authentication token.
      ```json
      {
         "code": 200,
         "message": "Login successfully",
         "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNeUFwcCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzM0MzY2NzA5LCJpYXQiOjE3MzQzNTk1MDksImp0aSI6IjYwMjVhNzJmLTdmYWQtNGYxYS04Y2FjLTllNjExOTkzMWM0NCIsInNjb3BlIjoiUk9MRV9BRE1JTiJ9.RhGhtazMVhvoFi952G_VnYB-hCd3nbqkEGE7u9wcNK8",
         "expiration": "2025-04-20 13:24"
      }
      ```
    - `401 Unauthorized`
      ```json
      {
         "code": 401,
         "message": "Unauthenticated"     
      }
      ```
    - `404 User not found`
      ```json
      {
         "code": 404,
         "message": "User not found"     
      }
      ```

#### 4. Register With Google
- **URL**: `GET /auth/register-google`
- **Description**: Đăng kí tài khoản bệnh nhân bằng tài khoản google
- **Response**:
    - `200 OK`: Return a link.
      ```json
      {
          "code": 200,
          "message": "Url generated successful",
          "data": "https://accounts.google.com/o/oauth2/auth?client_id=1054955116253-ltvgl9ddhvgddbtdencqit1es8h95mhe.apps.googleusercontent.com&redirect_uri=http://localhost:8080/auth/callback-google&response_type=code&scope=email+profile&state=register"
      }
      ```
  #### After access and register through google service
    - `200 OK`: Register successfully.
      ```json
      {
          "code": 200,
          "message": "Patient register successfully"
      }
      ```
    - `404 User existed`
      ```json
      {
         "code": 409,
         "message": "User already existed"     
      }
      ```
    - `401 Invalid email`
      ```json
      {
         "code": 401,
         "message": "Please enter email ends with @gmail.com"     
      }
      ```
            
### Users APIs

#### 1. Get my information
- **URL**:  `GET /users/my-info`
- **Description**: Cho phép người dùng xem thông tin cá nhân của mình.
- **Authorization**: `ADMIN` or `DOCTOR` or `PATIENT`
- **Response**:
    - `200 OK`:
      ```json
      {
          "statusCode": 200,
          "message": "My info fetch successfully",
          "data": {
            "id": 0,
            "email": "user1@gmail.com",
            "name": "NCT"
          }
      }
      ```
    - `404 User not found`
      ```json
      {
         "code": 404,
         "message": "User not found"     
      }
      ```

#### 2. Create Doctor
- **URL**:  `POST /users/create-doctor`
- **Description**: Cho phép admin tạo bác sĩ mới trong hệ thống.
- **Authorization**: `ADMIN`
- **Request Body**:
  ```json
  {
    "email": "admin2",
    "password": "123",
    "name": "Admin 2",
    "department": "Radiology",
    "experienceYears": 5,
    "specialization": "Radiologist"
  }
  ```
- **Response**:
    - `200 OK`:
      ```json
      {
          "statusCode": 200,
          "message": "Doctor created successfully"
      }
      ```
    - `401 User existed`
      ```json
      {
         "code": 409,
         "message": "User already existed"     
      }
      ```
    - `401 Invalid email`
      ```json
      {
         "code": 401,
         "message": "Please enter email ends with @gmail.com"     
      }
      ```


#### 3. Update User Info
- **URL**:  `PUT /users/update`
- **Description**: Cho phép user cập nhật thông tin cá nhân.
- **Authorization**: `ADMIN` or `DOCTOR` or `PATIENT`
- **Request Body**:
  ```json
  {
    "id": 2, 
    "email": "doctor100@gmail.com",
    "password": "123",
    "name": "Doctor 100"
  }   
  ```
- **Response**:
    - `200 OK`:
      ```json
      {
          "statusCode": 200,
          "message": "User updated successfully"
      }
      ```
    - `404 User not found`
      ```json
      {
         "code": 404,
         "message": "User not found"     
      }
      ```

#### 4. Delete User By Id
- **URL**:  `DELETE users/delete/{id}`
- **Description**: Cho phép admin xóa nguười dùng dựa vào id.
- **Authorization**: `ADMIN`
- **Path Variable**: long id
- **Response**:
    - `200 OK`:
      ```json
      {
          "statusCode": 200,
          "message": "User deleted successfully"
      }
      ```
    - `404 User not found`
      ```json
      {
         "code": 404,
         "message": "User not found"     
      }
      ```

#### 5. Get All Users
- **URL**: `GET /users/all-users`
- **Description**: Lấy ra toàn bộ user.
- - **Authorization**: `ADMIN`
- **Request Param**:
    - Page: defaultValue = 0
    - Size: defaultValue = 5
- **Response**:
    - `200 OK`: Fetched successfully.
      ```json
      {
          "code": 200,
          "message": "All users fetched successfully",
          "authenticated": false,
          "currentPage": 0,
          "totalPages": 4,
          "totalElements": 5,
          "data": [
            {
                "id": 1,
                "email": "admin@gmail.com",
                "name": "Admin",
                "role": "ADMIN"
            },
            {
                "id": 2,
                "email": "doctor1@gmail.com",
                "name": "Doctor One",
                "role": "DOCTOR"
            },
            {
                "id": 3,
                "email": "doctor2@gmail.com",
                "name": "Doctor Two",
                "role": "DOCTOR"
            },
            {
                "id": 4,
                "email": "doctor3@gmail.com",
                "name": "Doctor Three",
                "role": "DOCTOR"
            },
            {
                "id": 5,
                "email": "doctor4@gmail.com",
                "name": "Doctor Four",
                "role": "DOCTOR"
            }
        ]
      }
      ```
    - `200 OK` No users
      ```json
      {
         "code": 200,
         "message": "No users are found"     
      }
      ```

#### 5. Get All Doctors
- **URL**: `GET /users/all-doctors`
- **Description**: Lấy ra toàn bộ doctor.
- - **Authorization**: `ADMIN`
- **Request Param**:
    - Page: defaultValue = 0
    - Size: defaultValue = 5
- **Response**:
    - `200 OK`: Fetched successfully.
      ```json
      {
          "code": 200,
          "message": "All doctors fetched successfully",
          "authenticated": false,
          "currentPage": 0,
          "totalPages": 2,
          "totalElements": 5,
          "data": [
            {
                "id": 2,
                "email": "doctor1@gmail.com",
                "name": "Doctor One",
                "role": "DOCTOR"
            },
            {
                "id": 3,
                "email": "doctor2@gmail.com",
                "name": "Doctor Two",
                "role": "DOCTOR"
            },
            {
                "id": 4,
                "email": "doctor3@gmail.com",
                "name": "Doctor Three",
                "role": "DOCTOR"
            },
            {
                "id": 5,
                "email": "doctor4@gmail.com",
                "name": "Doctor Four",
                "role": "DOCTOR"
            },
            {
                "id": 6,
                "email": "doctor5@gmail.com",
                "name": "Doctor Five",
                "role": "DOCTOR"
            }
          ]
      }
      ```
    - `200 OK` No users
      ```json
      {
         "code": 200,
         "message": "No doctors are found"     
      }
      ```

#### 7. Get All Patients
- **URL**: `GET /users/all-patients`
- **Description**: Lấy ra toàn bộ patient.
- - **Authorization**: `ADMIN`
- **Request Param**:
    - Page: defaultValue = 0
    - Size: defaultValue = 5
- **Response**:
    - `200 OK`: Fetched successfully.
      ```json
      {
          "code": 200,
          "message": "All patients fetched successfully",
          "authenticated": false,
          "currentPage": 0,
          "totalPages": 2,
          "totalElements": 5,
          "data": [
            {
                "id": 7,
                "email": "patient1@gmail.com",
                "name": "Patient One",
                "role": "PATIENT"
            },
            {
                "id": 8,
                "email": "patient2@gmail.com",
                "name": "Patient Two",
                "role": "PATIENT"
            },
            {
                "id": 9,
                "email": "patient3@gmail.com",
                "name": "Patient Three",
                "role": "PATIENT"
            },
            {
                "id": 10,
                "email": "patient4@gmail.com",
                "name": "Patient Four",
                "role": "PATIENT"
            },
            {
                "id": 11,
                "email": "patient5@gmail.com",
                "name": "Patient Five",
                "role": "PATIENT"
            }
          ]
      }
      ```
    - `200 OK` No users
      ```json
      {
         "code": 200,
         "message": "No patients are found"     
      }
      ```