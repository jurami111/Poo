## 1. Respuestas al Reto de Rediseño (Explicación de la Solución)

El diseño original padecía de un alto acoplamiento (código espagueti) porque la clase `BankingService` centralizaba todas las reglas usando condicionales (`switch` statements). Para solucionarlo, se aplicó un diseño orientado a objetos:

*   **¿Qué reglas pertenecen a la identidad misma y cómo se ve la clase abstracta?**
    Las reglas sobre el límite diario, las operaciones permitidas y las validaciones extra (como el vencimiento de residencia o tener un tutor) le pertenecen exclusivamente a cada Identidad. Por ende, se creó la clase abstracta `Identity` con los métodos abstractos `getDailyLimit()`, `isAllowed()` y `validate()`. Sus cuatro subclases (`PersonalIdentity`, `BusinessIdentity`, `MinorIdentity`, `ForeignResidentIdentity`) sobrescriben estos métodos, encapsulando sus propios atributos y reglas sin mezclarse.
*   **¿Cómo dar un contrato común a los procesadores externos sin modificarlos?**
    Se utiliza el **Patrón Adapter**. Se crea una interfaz `ProcessorAdapter` (nuestro contrato común) y múltiples clases adaptadoras que implementan esta interfaz y "envuelven" (wrap) a las clases de los bancos externos que no podemos modificar.
*   **¿Dónde debería vivir la regla "los menores solo pueden usar National Bank"?**
    Debe vivir en un objeto de política separado (en el diagrama: `ProcessorRouter`). No debe ir en la Identidad (porque acopla el dominio con la infraestructura externa) ni en el Procesador (porque los bancos no dictan reglas de negocio interno).
*   **¿Deberían las operaciones ser una jerarquía también?**
    Sí. Se creó una clase abstracta `BankOperation` que hereda a operaciones específicas como `PayrollOperation` o `InternationalTransfer`. Esto elimina los campos "nulos" innecesarios; por ejemplo, ahora un depósito jamás tendrá un campo para una lista de nómina, porque esos datos le pertenecen solo a la subclase `PayrollOperation`.
*   **Conclusión de escalabilidad:** Si se quiere agregar la identidad "Senior Citizen" y el procesador "Crypto", el número de clases existentes que hay que modificar es **CERO**. Solo se crean las nuevas subclases correspondientes, cumpliendo el principio Open-Closed.

---

## 2. Diagrama de clases (Mermaid)

classDiagram
    class IdentityType {
        <<enumeration>>
        PERSONAL
        BUSINESS
        MINOR
        FOREIGN_RESIDENT
    }

    class User {
        -String id
        -String fullName
        -List~Identity~ identities
        +addIdentity(Identity identity)
        +findIdentity(IdentityType type) Identity
        +getId() String
        +getFullName() String
    }

    %% --- IDENTITY HIERARCHY ---
    class Identity {
        <<abstract>>
        -String documentNumber
        -String accountNumber
        +getDocumentNumber() String
        +getAccountNumber() String
        +getDailyLimit()* double
        +isAllowed(BankOperation operation)* boolean
        +validate(BankOperation operation, LocalDate today)* void
    }

    class PersonalIdentity {
        +getDailyLimit() double
        +isAllowed(BankOperation operation) boolean
        +validate(BankOperation operation, LocalDate today) void
    }
    
    class BusinessIdentity {
        -String companyName
        +getCompanyName() String
        +getDailyLimit() double
        +isAllowed(BankOperation operation) boolean
        +validate(BankOperation operation, LocalDate today) void
    }
    
    class MinorIdentity {
        -String guardianUserId
        +getGuardianUserId() String
        +getDailyLimit() double
        +isAllowed(BankOperation operation) boolean
        +validate(BankOperation operation, LocalDate today) void
    }
    
    class ForeignResidentIdentity {
        -String countryCode
        -LocalDate residencyExpiresOn 
        +getCountryCode() String
        +getResidencyExpiresOn() LocalDate
        +getDailyLimit() double
        +isAllowed(BankOperation operation) boolean
        +validate(BankOperation operation, LocalDate today) void
    }

    Identity <|-- PersonalIdentity : extends
    Identity <|-- BusinessIdentity : extends
    Identity <|-- MinorIdentity : extends
    Identity <|-- ForeignResidentIdentity : extends

    %% --- OPERATION HIERARCHY ---
    class BankOperation {
        <<abstract>>
        -double amount
        -String currency
        +getAmount() double
        +getCurrency() String
    }

    class StandardTransfer {
        -String destinationAccount
        +getDestinationAccount() String
    }

    class InternationalTransfer {
        -String destinationBic
        +getDestinationBic() String
    }

    class PayrollOperation {
        -List~String~ payrollAccounts
        +getPayrollAccounts() List~String~
    }

    BankOperation <|-- StandardTransfer : extends
    StandardTransfer <|-- InternationalTransfer : extends
    BankOperation <|-- PayrollOperation : extends

    class OperationResult {
        -boolean success
        -String reference
        -double fee
        -String message
        +success(String reference, double fee)$ OperationResult
        +failure(String message)$ OperationResult
        +isSuccess() boolean
    }

    %% --- INFRASTRUCTURE (PROCESSORS & ADAPTERS) ---
    class ProcessorAdapter {
        <<interface>>
        +process(Identity identity, BankOperation operation) OperationResult
        +supports(BankOperation operation) boolean
        +calculateFee(BankOperation operation, double totalAmount) double
    }

    class NationalBankAdapter {
        +process(Identity identity, BankOperation operation) OperationResult
    }

    class PacificBankAdapter {
        +process(Identity identity, BankOperation operation) OperationResult
    }

    class SwiftAdapter {
        +process(Identity identity, BankOperation operation) OperationResult
    }

    ProcessorAdapter <|.. NationalBankAdapter : implements
    ProcessorAdapter <|.. PacificBankAdapter : implements
    ProcessorAdapter <|.. SwiftAdapter : implements

    class NationalBankProcessor {
        -int sequence
        +postTransaction(String accountNumber, String kind, double amount, String counterparty) String
    }

    class PacificBankProcessor {
        -int sequence
        +submit(String customerRef, String operationCode, long amountInCents, String destination) String
        +submitPayroll(String customerRef, List~String~ accounts, long centsPerAccount) String
    }

    class SwiftGatewayProcessor {
        -int sequence
        +sendWire(String fromAccount, String toAccount, String bic, double amount, String currency) String
    }

    NationalBankAdapter *-- NationalBankProcessor : wraps
    PacificBankAdapter *-- PacificBankProcessor : wraps
    SwiftAdapter *-- SwiftGatewayProcessor : wraps

    %% --- CORE SERVICES ---
    class DailyUsageTracker {
        -Map~String, Double~ usage
        +usedOn(Identity identity, LocalDate day) double
        +record(Identity identity, LocalDate day, double amount) void
    }

    class AuditLog {
        -List~String~ entries
        +record(String entry) void
        +getEntries() List~String~
    }

    class ProcessorRouter {
        -List~ProcessorAdapter~ adapters
        +route(Identity identity, BankOperation operation, String processorName) ProcessorAdapter
    }

    class BankingService {
        -ProcessorRouter router
        -DailyUsageTracker usageTracker
        -AuditLog auditLog
        +execute(User user, IdentityType identityType, BankOperation operation, String processorName, LocalDate today) OperationResult
        +getAuditLog() AuditLog
    }

    %% --- RELATIONSHIPS ---
    User "1" *-- "*" Identity : contains
    
    BankingService *-- "1" ProcessorRouter : owns
    BankingService *-- "1" DailyUsageTracker : owns
    BankingService *-- "1" AuditLog : owns

    ProcessorRouter *-- "*" ProcessorAdapter : registers

    BankingService ..> User : uses
    BankingService ..> BankOperation : processes
    BankingService ..> OperationResult : returns

---

## 3. Poliformismo

* Se usa en la clase BankingService, ahora, en lugar de preguntar con condicionales qué tipo de usuario u operación se está procesando, el servicio simplemente recibe referencias a las clases abstractas Identity y BankOperation.
* Ademas, para la clase identity, a la hora de llamar las funciones identity.validate o identity.isAllowed, se certifica que, aunque se este llamando a identity, en realidad se esta usando una subclase.

---

## 4. Wrappers

* En el diagrama, los wrappings se demuestran claramente con las relaciones de composición (*-- wraps) entre los adaptadores y los procesadores externos
* El wrapper implementado ayuda a estandarizar la forma en la que se pide el dinero los bancos externos al convertirlos en centavos. Ademas, se vuelve mas facil de mantener la conexion con todos los demas bancos ya que es una forma de estandarizar todo 

