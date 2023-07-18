# invoice-system


<details>
  <summary>Project Requirements</summary>

- Bir müşteri bilgisi alıp kayıt eden, bir fatura bilgisi kayıt eden ve bu bilgileri sorgulayan restApi ler olacak.
  
- Bir de ödenmiş statüsünde gözüken fatura kaydı oluşturalım. Müşterinin faturası sorgulandığında ödenmemiş faturanın bulunmadığına dair response code ve mesaj dönülsün. (Fatura sorgulama faturaId ve müşteri numarası ile yapılmalı)
  
- Oluşturulan müşteri kaydı ve fatura kaydı için id bilgisi ile silme işlemleri yapan 2 servis olsun.
  
- Fatura kaydı oluşturulacak, kayıt sorgulanabilecek.
  
- Müşteri bilgisi update eden bir servis olacak.

- Bu işlemlerin postgreSql e giden sorgular ile yapacağız. Respository bağlantısı olmalı.
  
- Proje bir maven projesi olacak. Springboot framework ü ile ve SOLID prensiplerine uygun şekilde yazılacak.


  3 adet tablo yeterli. Fatura, User, Payment

  Payment işlemini doğrudan yapılmış gibi hazır kayıt oluşturulması
yeterli.

  Servisler ResponseEntity tipinde cevap dönmeli.
</details>

## Rest API Description
  
  - http://localhost:8080/v1/users/save -> It is used in the customer registration section of the system. Name and surname fields should not be blank. Necessary validation procedures have been carried out. 
  ```diff
  ! Body JSON
  {"name":"Sefa Mert", "surname":"Gungor"}
  ```
  
  ```diff
  + Response
  {
    "Status": 200,
    "Message": "User created with entered user information.",
    "Customer": {
        "subscriberId": 10,
        "name": "Sefa Mert",
        "surname": "Gungor",
        "subscriberNo": 10
    }
}
  ```
  - http://localhost:8080/v1/users/get -> Brings all customers registered in the system. Return customer list, HttpStatus, message as reponse.
   ```diff
  + Response
  {
    "Status": 200,
    "Message": "Customer list brought.",
    "Customer": [
        {
            "subscriberId": 1,
            "name": "Sefa",
            "surname": "Gungor",
            "subscriberNo": 1
        },
        {
            "subscriberId": 2,
            "name": "Mert",
            "surname": "Gungor",
            "subscriberNo": 2
        }
    ]
}
  ```
  - http://localhost:8080/v1/users/get/{subscriberId} -> Brings the desired customer with subscriberId. Return customer, HttpStatus and message as response.
  ```diff
  + Response
  {
    "Status": 200,
    "Message": "Customer brought.",
    "Customer": {
        "subscriberId": 1,
        "name": "Sefa",
        "surname": "Gungor",
        "subscriberNo": 1
    }
}
  ```
  - http://localhost:8080/v1/users/delete/{subscriberId} -> Deletes the desired customer with subscriberId. Return deleted customer, Httpstatus and message as response.
  ```diff
  + Response
  {
    "Status": 200,
    "Message": "Customer deleted.",
    "Customer": {
        "subscriberId": 1,
        "name": "Sefa",
        "surname": "Gungor",
        "subscriberNo": 1
    }
}
  ```
  - http://localhost:8080/v1/users/update/{subscriberId}?surname=&name= -> Updates the desired customer. (name and surname @RequestParam)
  ```diff
  + Response
 {
    "Status": 200,
    "Message": "Customer information updated.",
    "Customer": {
        "subscriberId": 2,
        "name": "Sefa Mert",
        "surname": "Gungor",
        "subscriberNo": 2
    }
}
  ```
  - http://localhost:8080/v1/invoice/save -> It is used in the invoice registration section of the system. subscriberNo and invoiceAmount fields should not be blank. Necessary validation procedures have been carried out.
  ```diff
  ! Body JSON
  {"subscriberNo":"2", "invoiceAmount":"896"}
  ```
  ```diff
  + Response
  {
    "Invoice": {
        "invoiceNo": 11,
        "subscriberNo": 18,
        "invoiceAmount": 896.0,
        "invoiceProDate": "2022-07-24",
        "paymentControl": 0
    },
    "message": "Invoice created.",
    "status": 200
}
  ```
  - http://localhost:8080/v1/invoice/get -> Brings all invoices registered in the system. Return invoice list, HttpStatus, message as reponse.
  ```diff
  + Response
  {
    "Invoice": [
        {
            "invoiceNo": 3,
            "subscriberNo": 14,
            "invoiceAmount": 5000.0,
            "invoiceProDate": "2022-07-24",
            "paymentControl": 0
        },
        {
            "invoiceNo": 9,
            "subscriberNo": 18,
            "invoiceAmount": 896.0,
            "invoiceProDate": "2022-07-24",
            "paymentControl": 1
        }
    ],
    "message": "Invoice list brought.",
    "status": 200
}
  ```
  - http://localhost:8080/v1/invoice/get/{invoiceNo} -> Brings the desired invoice with invoiceNo. Return invoice, HttpStatus and message as response.
  ```diff
  + Response
  {
    "Invoice": {
        "invoiceNo": 3,
        "subscriberNo": 14,
        "invoiceAmount": 5000.0,
        "invoiceProDate": "2022-07-24",
        "paymentControl": 0
    },
    "message": "Invoice Brought",
    "status": 200
}
  ```
  - http://localhost:8080/v1/invoice/delete/{invoiceNo} -> Deletes the desired customer with invoiceNo. Return deleted invoice, Httpstatus and message as response.
  ```diff
  + Response
  {
    "Invoice": {
        "invoiceNo": 11,
        "subscriberNo": 18,
        "invoiceAmount": 896.0,
        "invoiceProDate": "2022-07-24",
        "paymentControl": 0
    },
    "message": "Invoice Deleted.",
    "status": 200
}
  ```
  - http://localhost:8080/v1/invoice/invoiceInquiry/{invoiceNo} -> Performs invoice payment inquiry with invoiceNo. If status equals 1 invoice paid, if status equals zero invoice not paid. 
  ```diff
  + Response
  {
    "Invoice": {
        "invoiceNo": 9,
        "subscriberNo": 18,
        "invoiceAmount": 896.0,
        "invoiceProDate": "2022-07-24",
        "paymentControl": 1
    },
    "message": "Inquiry invoice paid Amount Paid: 896.0",
    "status": 200
}
  ```
  - http://localhost:8080/v1/payment/invoiceInquiry/{subscribeNo} -> Performs invoice payment inquiry with subscribeNo. If such a record exists, there is no unpaid invoice for the user.
  ```diff
  + Response
  {
    "Invoice": {
        "id": 1,
        "paymentAmount": 500.0,
        "paymentDate": "2022-02-05",
        "subscriberNo": 14
    },
    "message": "The customer has no unpaid invoices.",
    "status": 200
}
  ```
  
