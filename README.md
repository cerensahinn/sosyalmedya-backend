# 📱 Sosyal Medya Servisi (Java 17 + Spring Boot 3 + PostgreSQL)

Bu proje, kullanıcıların **gönderi paylaşabildiği**, **beğeni** ve **yorum** yapabildiği bir sosyal medya platformunun **backend ** uygulamasıdır.  
Uygulama, **REST API** mimarisi ile geliştirilmiştir ve veritabanında saklanan **Token tabanlı kimlik doğrulama** mekanizmasıyla güvenli erişim sağlar.  
Spring Boot’un sağladığı modüler yapı sayesinde servisler katmanlı olarak ayrılmıştır (Controller → Service → Repository).

> 💡 Proje, Spring Security kullanılmadan tamamen özel bir “token doğrulama” mantığı ile tasarlanmıştır.  
> Bu sayede eğitim ve test amaçlı projelerde kimlik doğrulama mekanizmasının mantığı net bir şekilde izlenebilir.

---

## 🎯 Projenin Amacı
Bu proje, bir sosyal medya platformunun temel özelliklerini (kayıt olma, giriş yapma, gönderi paylaşma, beğenme, yorum yapma, hesap yönetimi) simüle etmeyi amaçlar.  
Amaç; **Spring Boot ile gerçek dünya CRUD operasyonları**, **DTO kullanımı**, **Exception Handling** ve **token yönetimi** gibi temel kavramları uygulamalı biçimde göstermek.

---

## ⚡ Genel Özellikler
- Kullanıcılar kayıt olup giriş yapabilir.
- Gönderi oluşturabilir, silebilir ve güncelleyebilir.
- Gönderilere yorum yapabilir veya beğenebilir.
- Her kullanıcı sadece kendi gönderi ve yorumlarını düzenleyebilir/silebilir.
- Admin kullanıcı tüm hesapları yönetebilir.
- Tüm isteklerde kimlik doğrulama **Authorization: Token <token>** başlığıyla yapılır.

---

## 🧩 Teknolojiler
| Katman | Teknoloji |
|--|--|
| Backend | Java 17, Spring Boot 3 (Web, Data JPA, Validation) |
| Veritabanı | PostgreSQL + PostgreSQL JDBC Driver |
| ORM | Hibernate (Spring Data JPA üzerinden) |
| Build | Maven |
| IDE | IntelliJ IDEA |
| Test / API | Postman |

---

## 💻 Önkoşullar (Requirements)

Projeyi çalıştırmadan önce aşağıdaki araçların sisteminizde kurulu olduğundan emin olun:

| Araç | Minimum Sürüm | Açıklama |
|--|--|--|
| **Java JDK** | 17+ | Spring Boot 3 için zorunludur. |
| **Maven** | 3.8+ | Projeyi build etmek ve bağımlılıkları yönetmek için gereklidir. |
| **PostgreSQL** | 13+ | Veritabanı olarak kullanılır. |
| **IntelliJ IDEA / VS Code** | - | Geliştirme ortamı (IDE). |
| **Postman** | - | API testleri için kullanılır. |

> 💡 Not:  
> Java ve Maven kurulumlarını terminalde `java -version` ve `mvn -version` komutlarıyla doğrulayabilirsiniz.  
> PostgreSQL için `psql --version` komutu kullanılabilir.

---

## ⚙️ Kurulum ve Çalıştırma

### 1️⃣ PostgreSQL Veritabanı Oluşturma

Projeyi ilk kez çalıştırmadan önce veritabanını oluşturun:

```sql
CREATE DATABASE sosyalmedya;
-- Opsiyonel olarak kullanıcı da oluşturabilirsiniz:
-- CREATE USER sosyal WITH ENCRYPTED PASSWORD 'sifre';
-- GRANT ALL PRIVILEGES ON DATABASE sosyalmedya TO sosyal;
```
---

### 2️⃣ `application.yml` Yapılandırma Örneği

Uygulamanın çalışması için gerekli veritabanı ve port ayarlarını aşağıdaki şekilde düzenleyin:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sosyalMedya
    username: postgres
    password: postgres123.
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080

```

## 👤 Hazır ADMIN

| Alan | Değer |
|--|--|
| E-posta | `admin@example.com` |
| Şifre  | `Admin123!!` |

> Admin kullanıcısı veritabanında zaten kayıtlıdır.  
> Bu bilgiler test ve Postman doğrulamaları için kullanılabilir.  
> Üretim ortamında mutlaka değiştirilmelidir.

---

## 🔐 Kimlik Doğrulama Akışı

1. **Signup** – Yeni kullanıcı kaydı oluşturur  
   `POST /api/auth/signup`

2. **Login** – Giriş yapar ve sistem token üretir  
   `POST /api/auth/login`

3. **Authorization** – Her isteğe token eklenmelidir  
   `Authorization: Token <token_degeri>`

4. **Logout** – Oturumu sonlandırır  
   `POST /api/auth/logout`

> 💡 Token, giriş yapan kullanıcı için üretilir ve veritabanında aktif olarak saklanır.  
> Token süresi dolduğunda veya logout çağrısı yapıldığında pasifleştirilir.

---

---

## 🧭 Uç Noktalar (Endpoints)

### 01 - AUTH
| HTTP | Endpoint | Açıklama |
|--|--|--|
| POST | `/api/auth/signup` | Yeni kullanıcı kaydı oluşturur |
| POST | `/api/auth/login` | Giriş yapar ve token üretir |
| GET  | `/api/auth/logout` | Oturumu sonlandırır |

---

### 02 - USERS
| HTTP | Endpoint | Açıklama |
|--|--|--|
| GET | `/api/users/{id}` | Kullanıcı bilgilerini getirir |
| PUT | `/api/users/me/password` | Şifre değiştirme |
| DELETE | `/api/users/me` | Kullanıcı kendi hesabını siler |

---

### 03 - POSTS
| HTTP | Endpoint | Açıklama |
|--|--|--|
| POST | `/api/posts` | Yeni gönderi oluşturur |
| GET | `/api/posts/{id}` | Tek bir gönderiyi getirir |
| PUT | `/api/posts/{id}` | Gönderiyi günceller |
| DELETE | `/api/posts/{id}` | Gönderiyi siler |
| GET | `/api/posts?page=0&size=10` | Gönderileri sayfalı listeler |
| POST | `/api/posts/{id}/view` | Gönderi görüntülenme sayısını artırır |

---

### 04 - COMMENTS
| HTTP | Endpoint | Açıklama |
|--|--|--|
| POST | `/api/posts/{id}/comments` | Gönderiye yorum ekler |
| DELETE | `/api/comments/{id}` | Yorumu siler |
| GET | `/api/posts/{id}/comments` | Yorumları listeler |

---

### 05 - LIKES
| HTTP | Endpoint | Açıklama |
|--|--|--|
| POST | `/api/posts/{id}/likes` | Gönderiyi beğenme |
| DELETE | `/api/posts/{id}/likes` | Gönderiyi beğenmekten vazgeçme |

---

### 06 - ADMIN
| HTTP | Endpoint | Açıklama |
|--|--|--|
| DELETE | `/api/admin/users/{id}` | Admin bir kullanıcıyı siler |
| GET | `/api/admin/users` | Tüm kullanıcıları getirir |

---


### 📘 Örnek JSON Gövdeleri

**Signup**
```json
{ "ad": "Ceren", "soyad": "Sahin", "email": "ceren@example.com", "sifre": "123456" }
```
**Login**
```json

{ "email": "ceren@example.com", "sifre": "123456" }


```


## ⚠️ Hata Formatı

Bu API’de hatalar aşağıdaki JSON yapısıyla döner:

```json
{
  "detayMesaj": "<teknik açıklama>",
  "detaySinifi": "<fırlatılan exception sınıfı>",
  "mesaj": "<genel/son kullanıcı mesajı>"
}
```
### 📘 Örnek Hata Mesajı
Beğeni zaten var (Like Post)
```json

{
"detayMesaj": "Bu gönderi zaten beğenilmiş",
"detaySinifi": "com.cerensahin.sosyalmedya.ortak.exception.gonderi.BegeniZatenVarException",
"mesaj": "Beklenmeyen bir hata oluştu."
}
```
---

## 📦 Postman Collection

Projeye ait Postman koleksiyonu aşağıdaki klasör yapısına sahiptir:
```
01 - Auth
02 - Users
03 - Posts
04 - Comments
05 - Likes
06 - Admin
```
---

## 🌍 Environment (Ortam Değişkenleri)

Postman ortam dosyasında aşağıdaki değişkenler tanımlanmalıdır:

| Değişken Adı | Örnek Değer | Açıklama |
|--|--|--|
| `baseUrl` | `http://localhost:8080/api` | API'nin temel URL adresi |
| `accessToken` | `Token <token_degeri>` | Giriş sonrası alınan erişim anahtarı |

> 💡 `{{baseUrl}}` değişkeni her endpoint'in başına otomatik eklenir.  
> Giriş (Login) sonrası dönen token değerini `{{accessToken}}` olarak tanımlayıp, header'da `Authorization: Token {{accessToken}}` formatında kullanmalısınız.

---

## 🔁 Koleksiyon Akışı (Test Sırası)

Projeyi Postman üzerinde test ederken aşağıdaki sıralamayı takip edin:

1️⃣ **Signup** – Yeni kullanıcı kaydı oluştur  
2️⃣ **Login** – Token al  
3️⃣ **Yetkili İşlemler**  
  • Gönderi oluştur / listele / güncelle / sil  
  • Yorum ekle veya kaldır  
  • Gönderiyi beğen veya beğenmekten vazgeç  
4️⃣ **Logout** – Oturumu sonlandır

> 💡 Koleksiyon akışı şu dosyalarla birlikte gelir:
- postman/SosyalMedya.postman_collection.json
- postman/SosyalMedya.postman_environment.json
---

## 🧠 Varsayımlar & Kısıtlar

**Varsayımlar**
- Uygulamada **Spring Security** kullanılmamıştır; kimlik doğrulama tamamen özel olarak yazılmıştır.
- Her istek için kimlik doğrulama bilgisi, **`Authorization: Token <token>`** başlığında gönderilmelidir.
- **Admin** kullanıcısı veritabanında önceden kayıtlıdır ve testlerde kullanılabilir.
- **PostgreSQL** veritabanı varsayılan port (**5432**) üzerinden yerel ortamda çalışmaktadır. Uzaktan bağlantı için `application.yml` dosyasında güncelleme yapılabilir.

---

**Kısıtlar**
- **Dosya yükleme** (örneğin gönderiye fotoğraf ekleme) işlemleri yalnızca örnek amaçlıdır; büyük boyutlu veya bulut tabanlı dosya depolama sistemi kullanılmamıştır.
- Uygulamada **çok fazla istek ** veya **şifre deneme koruması** gibi güvenlik sınırları yoktur.
- Veritabanı yapısı, geliştirme kolaylığı için **otomatik güncellenmektedir** (`ddl-auto: update`). 
- Bazı hata durumlarında sistem genel hata kodu (**500 Internal Server Error**) dönebilir.

