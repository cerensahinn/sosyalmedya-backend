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

