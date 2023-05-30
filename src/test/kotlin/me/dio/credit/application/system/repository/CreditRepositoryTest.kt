package me.dio.credit.application.system.repository

import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.UUID


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit


    @BeforeEach fun setup() {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))



    }

    @Test
    fun `should find credit by credit code` () {
        //given
        var creditCode1 = UUID.fromString("38d53fff-45bd-4915-881f-702c0dc1f876")
        var creditCode2 = UUID.fromString("ecd6aa69-e90e-43bb-b2cb-db2248e43cee")
        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2
        //when
        var fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1)!!
        var fakeCredit2: Credit = creditRepository.findByCreditCode(creditCode2)!!

        //then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
    }

    private fun buildCredit(

            creditValue: BigDecimal = BigDecimal.valueOf(500.0),
            dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
            numberOfInstallments: Int = 5,
            customer: Customer
    ): Credit = Credit(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            customer = customer
    )







    private fun buildCustomer (

            firstName: String = "Joana",
            lastName: String = "Sandra",
            cpf: String = "747.551.430-71",
            email: String = "joana_sandra@gmail.com",
            password: String = "123456",
            zipCode: String = "3500",
            street: String = "Rua Madalena",
            income: BigDecimal = BigDecimal.valueOf(1000.0),
           // id: Long = 1L
    ) = Customer (

            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = Address(
                    zipCode = zipCode,
                    street = street,
            ),
            income = income,
       //     id = id

    )
}