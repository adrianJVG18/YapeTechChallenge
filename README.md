# Yap      eTechChallenge
mini project for technical interview at Yape

+ Clean Arquitecture: La estructura del proyecto tomo como Guia a la arquitectura clean para la modularizacion de las capas. 
                      Cabe aclarar, que la arquitectura clean es una Guia, adaptable a su respectivo contexto de desarrollo, y mientras se sigan sus principios, 
                      se pueden obtener sus principales ventajas, Testeabilidad, Escalabilidad y Modularizacion.

                      Cabe aclarar que esta es una de las posibles versiones de la arquitectura clean en contexto Mobile, donde intente aplicar lo mejor posible 
                      sus conceptos principales, incluso cuando pareciera no ser algo necesario.
                      
  Dichas capas, desde la mas interna a la mas externas son las siguientes:

  - Entity:   Aca esta el modelado del nucleo del #Negocio. En este caso, las Recetas [Recipe] con sus respectivos campos con la notacion @Serializable
  
  - Data:     Aca estan las librerias y operaciones realizables sobre las entidades, y a su vez estan las implementaciones 
              de como acceder a dicha informacion, bien sea localmente o en el remotamente la ubicacion de dichas fuentes de estos 
              datos conocidas comunmente como "Repositorios". En pocas palabras, cada fuente de datos que la app implemente, aca esta definida
              
  - Domain:   Es la capa central de la arquitectura Clean y contiene la lógica de negocio de la aplicación. La implementación de esta capa se puede 
              realizar mediante patrones de diseño como DDD o mediante la definición de interfaces y contratos. En dominios mas complicados, esta 
              capa contiene detalles de implementacion del negocio en una serie de abstracciones utiles para el equipo. 
              (Disclaimer:  Para la escala actual del proyecto, en la capa Dominio hubiera sido mas que suficiente definir una interface 
                            que contuviera dicha abstraccion del dominio, pero quize aprovechar la oportunidad para practicar el uso del patron 
                            de diseño "Usecase" potenciado con Hilt, ya que para dominios mas complicados, es un patron muy util, pero en este caso, 
                            es Sobre-Ingenieria, innecesario, un "Overkill" 
              
  - Adapter:  Esta capa suelen estar intermediarios que permitan que los datos fluyan "desde el exterior" a la UI a un formato mas conveniente para esta
              ultima, y viceversa. Por ejemplo, en el Patron de diseño MVP, aca estarian los Presenters, en el MVVM, aca estarian los ViewModels.

  -Framework: Esta es la capa mas externa, aca se definen todas las interacciones con el usuario mediante su interfaz, y a su vez cualquier controlador / Manager 
              de algun componente del Hardware del dispositivo (ej. Camara, sensores, Bluetooth, Gestor de Archivos, etc...), o los componentes vitales de una 
              App nativa de android (Servicios, Activities, Fragmentos, Content Providers), Estan definidas sus implementaciones en Este nivel


+ Una vez aclarado la idea principal detras de la definicion de la arquitectura y como esta organizado, se mencionarann los principales patrones de diseño usados:

  - MVVM:       (Model View ViewModel) Su principal ventaja es que almacena en caché el estado y lo conserva durante los cambios de configuración.
  - Usecase:    Es un encapsulador de la logica de negocio, un patron de diseño util en el DDD (Domain Driven Development), en este caso fue un patron que no 
                dio grandes beneficios en esta escala del proyecto, solo lo implemente como prueba de concepto.
  - Repository: En la capa de Datos, el patron repositorio es la abstraccion de la fuente de datos y sus operaciones, aca solo esta definido, si se 
                quiere llamar asi, el "contrato" que tiene que seguir cualquier fuente de datos asociado a alguna Entidad. En este caso, cualquier "RecipeRepository" 
                tiene que implementar las operaciones que se contemplan para la Entidad "Recipe"
 

+ Por ultimo, se mencionara cada libreria y dependencia externa de este proyecto y su proposito:

  - Google Maps Platform: / Play Services Map & Location : Provee soporte para implementacion de los Mapas de Google.

  - Navigation: Componente de Android Jetpack que simplifica y esquematiza en un Grafo el como se navega entre cada "Pantalla" en la App.

  - Viewmodel: Componente de Android Jetpack que provee cache del Estado de la Vista a traves del ciclo de vida de la App.
  
  - Hilt: Motor de Inyeccion de dependencias desarollado sobre la base de Dagger2.

  - Retrofit: Simplifica la realizacion de peticiones HTTP en la App.

  - Picasso: Simplifica la carga, cache y muestra de una imagen en un componente diseñado para mostrar Imagenes. Esta se uso para a partir de URL de 
              Imagenes que no almacene en Mockable, y asi solo almacenar sus URLs

  - Viewbinding: facilita y mejora la performance de la Vinculacion de Vistas con codigo con respecto a las vistas

  - Flow: Para el manejo de la concurrencia y tareas en hilos mediante Corutinas

