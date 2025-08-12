# JavaFX com HTML

Este é um projeto simples que utiliza **JavaFX** para exibir uma página HTML (`index.html`) em um `WebView`.

## 🚀 Como executar

### 1️⃣ Requisitos
- **Java JDK 17+**
- **JavaFX SDK 17+**
- Um editor como **VS Code** ou **IntelliJ**

Baixe o JavaFX SDK em:  
https://gluonhq.com/products/javafx/

---

### 2️⃣ Estrutura de pastas

projetovs/
├── src/
│ ├── App.java
│ └── index.html
├── lib/ (JavaFX SDK)
└── README.md

---

### 3️⃣ Compilação pelo terminal (Git Bash ou CMD)

**No Windows:**
```bash
# Ir para a pasta do projeto
cd C:/projetovs

# Compilar
javac --module-path "C:/javafx-sdk-17.0.12/lib" --add-modules javafx.controls,javafx.web -d out src/*.java

# Executar
java --module-path "C:/javafx-sdk-17.0.12/lib" --add-modules javafx.controls,javafx.web -cp out App
