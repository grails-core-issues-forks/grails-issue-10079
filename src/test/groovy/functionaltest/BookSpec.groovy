package functionaltest

import grails.test.mixin.TestFor
import spock.lang.Issue
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Book)
class BookSpec extends Specification {

    void "Test validating a book"() {
        expect:"The book validates"
            new Book(title:"The Stand").validate()
    }

    @Issue('grails/grails-core#10079')
    void 'Test that auto-timestamp properties are excluded from mass property binding'() {
        given:
        def book = new Book()

        when:
        book.properties = [title: 'Some Title',
                           dateCreated: 'some value',
                           lastUpdated: 'some value']
        
        println "="*100
        println "book.errors -> " + book.errors
        println "="*100

        then:
        !book.hasErrors()
        book.title == 'Some Title'
        book.dateCreated == null
        book.lastUpdated == null
    }
}
