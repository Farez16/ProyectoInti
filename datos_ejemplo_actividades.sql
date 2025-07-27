-- =====================================================
-- DATOS DE EJEMPLO PARA ACTIVIDADES UNIDAD 1
-- =====================================================
-- Este archivo contiene datos de ejemplo para probar las actividades 1, 2 y 3
-- Ejecuta este script en tu base de datos MySQL 'proyecto_kiwchaa'

USE proyecto_kiwchaa;

-- =====================================================
-- 1. INSERTAR ACTIVIDADES BASE
-- =====================================================

-- Limpiar datos existentes (opcional)
-- DELETE FROM progreso_usuario WHERE id_unidad = 1;
-- DELETE FROM emparejar_opciones WHERE id_actividad IN (SELECT id_actividad FROM actividades WHERE id_unidad = 1);
-- DELETE FROM drag_drop_items WHERE id_actividad IN (SELECT id_actividad FROM actividades WHERE id_unidad = 1);
-- DELETE FROM actividades WHERE id_unidad = 1;

-- Insertar actividades para la Unidad 1
INSERT INTO actividades (id_actividad, id_unidad, tipo, titulo, descripcion, puntuacion_maxima) VALUES
(1, 1, 'pregunta_respuesta', 'Preguntas sobre Saludos', 'Actividad de preguntas y respuestas sobre saludos en kichwa', 100),
(2, 1, 'drag_drop', 'Arrastrar y Soltar', 'Actividad de arrastrar elementos a sus posiciones correctas', 100),
(3, 1, 'emparejar', 'Emparejar Palabras', 'Actividad de emparejar palabras con imágenes', 100),
(4, 1, 'emparejar', 'Asociación Palabra-Imagen', 'Juego de asociar palabras kichwa con imágenes', 100);

-- =====================================================
-- 2. DATOS PARA ACTIVIDAD 1 (PREGUNTA-RESPUESTA)
-- =====================================================

-- Insertar preguntas de ejemplo para la actividad 1
INSERT INTO evaluaciones (id_evaluacion, id_unidad, pregunta, opcion_a, opcion_b, opcion_c, opcion_d, respuesta_correcta, puntuacion) VALUES
(1, 1, '¿Cómo se dice "Hola" en kichwa?', 'Allillachu', 'Tupananchiskama', 'Alli puncha', 'Sumak kausay', 'A', 10),
(2, 1, '¿Qué significa "Allillachu"?', 'Adiós', 'Hola/¿Cómo estás?', 'Gracias', 'Buenos días', 'B', 10),
(3, 1, '¿Cómo se dice "Gracias" en kichwa?', 'Yupaychani', 'Kushikuni', 'Llakikuni', 'Munani', 'A', 10),
(4, 1, '¿Qué significa "Tupananchiskama"?', 'Hola', 'Gracias', 'Hasta luego', 'Buenos días', 'C', 10),
(5, 1, '¿Cómo se dice "Buenos días" en kichwa?', 'Alli tuta', 'Alli puncha', 'Alli chishi', 'Allillachu', 'B', 10);

-- =====================================================
-- 3. DATOS PARA ACTIVIDAD 2 (DRAG & DROP)
-- =====================================================

-- Insertar elementos para drag & drop
INSERT INTO drag_drop_items (id_item, id_actividad, texto, recurso_url, posicion_destino, es_correcto) VALUES
(1, 2, 'Allillachu', '/audios/allillachu.mp3', 1, 1),
(2, 2, 'Yupaychani', '/audios/yupaychani.mp3', 2, 1),
(3, 2, 'Tupananchiskama', '/audios/tupananchiskama.mp3', 3, 1),
(4, 2, 'Alli puncha', '/audios/alli_puncha.mp3', 4, 1),
(5, 2, 'Sumak kausay', '/audios/sumak_kausay.mp3', 5, 1);

-- =====================================================
-- 4. DATOS PARA ACTIVIDAD 3 (EMPAREJAR PALABRA-IMAGEN)
-- =====================================================

-- Insertar pares correctos palabra-imagen para la actividad 3/4
INSERT INTO emparejar_opciones (id_opcion, id_actividad, texto_origen, recurso_origen_url, texto_destino, recurso_destino_url, es_correcto) VALUES
-- Pares correctos con imágenes
(1, 4, 'Palabra', NULL, 'allpa', '/imagenes/tierra.jpg', 1),
(2, 4, 'Palabra', NULL, 'yaku', '/imagenes/agua.jpg', 1),
(3, 4, 'Palabra', NULL, 'inti', '/imagenes/sol.jpg', 1),
(4, 4, 'Palabra', NULL, 'killa', '/imagenes/luna.jpg', 1),
(5, 4, 'Palabra', NULL, 'urku', '/imagenes/montaña.jpg', 1),
(6, 4, 'Palabra', NULL, 'sacha', '/imagenes/arbol.jpg', 1),

-- Distractores (palabras sin imagen)
(7, 4, 'Palabra', NULL, 'runa', NULL, 0),
(8, 4, 'Palabra', NULL, 'wasi', NULL, 0),
(9, 4, 'Palabra', NULL, 'mama', NULL, 0),
(10, 4, 'Palabra', NULL, 'tayta', NULL, 0),
(11, 4, 'Palabra', NULL, 'wawa', NULL, 0),
(12, 4, 'Palabra', NULL, 'ayllu', NULL, 0);

-- =====================================================
-- 5. DATOS ADICIONALES PARA EMPAREJAR (ACTIVIDAD 2)
-- =====================================================

-- Insertar opciones de emparejar para la actividad 2
INSERT INTO emparejar_opciones (id_opcion, id_actividad, texto_origen, recurso_origen_url, texto_destino, recurso_destino_url, es_correcto) VALUES
-- Pares para emparejar español-kichwa
(13, 2, 'Hola', NULL, 'Allillachu', NULL, 1),
(14, 2, 'Gracias', NULL, 'Yupaychani', NULL, 1),
(15, 2, 'Adiós', NULL, 'Tupananchiskama', NULL, 1),
(16, 2, 'Buenos días', NULL, 'Alli puncha', NULL, 1),
(17, 2, 'Buenas noches', NULL, 'Alli tuta', NULL, 1);

-- =====================================================
-- 6. RECURSOS MULTIMEDIA (OPCIONAL)
-- =====================================================

-- Insertar recursos multimedia relacionados
INSERT INTO recursos (id_recurso, id_unidad, id_leccion, id_actividad, tipo_recurso, nombre_archivo, ruta_archivo, descripcion) VALUES
(1, 1, NULL, 1, 'audio', 'allillachu.mp3', '/audios/allillachu.mp3', 'Pronunciación de "Allillachu"'),
(2, 1, NULL, 1, 'audio', 'yupaychani.mp3', '/audios/yupaychani.mp3', 'Pronunciación de "Yupaychani"'),
(3, 1, NULL, 3, 'imagen', 'tierra.jpg', '/imagenes/tierra.jpg', 'Imagen de tierra para "allpa"'),
(4, 1, NULL, 3, 'imagen', 'agua.jpg', '/imagenes/agua.jpg', 'Imagen de agua para "yaku"'),
(5, 1, NULL, 3, 'imagen', 'sol.jpg', '/imagenes/sol.jpg', 'Imagen de sol para "inti"'),
(6, 1, NULL, 3, 'imagen', 'luna.jpg', '/imagenes/luna.jpg', 'Imagen de luna para "killa"'),
(7, 1, NULL, 3, 'imagen', 'montaña.jpg', '/imagenes/montaña.jpg', 'Imagen de montaña para "urku"'),
(8, 1, NULL, 3, 'imagen', 'arbol.jpg', '/imagenes/arbol.jpg', 'Imagen de árbol para "sacha"');

-- =====================================================
-- 7. VERIFICACIÓN DE DATOS INSERTADOS
-- =====================================================

-- Consultas para verificar que los datos se insertaron correctamente
SELECT 'ACTIVIDADES INSERTADAS:' as Info;
SELECT id_actividad, tipo, titulo FROM actividades WHERE id_unidad = 1;

SELECT 'PREGUNTAS PARA ACTIVIDAD 1:' as Info;
SELECT id_evaluacion, pregunta, respuesta_correcta FROM evaluaciones WHERE id_unidad = 1;

SELECT 'ELEMENTOS DRAG & DROP:' as Info;
SELECT id_item, texto, posicion_destino FROM drag_drop_items WHERE id_actividad = 2;

SELECT 'PARES PARA EMPAREJAR (ACTIVIDAD 3):' as Info;
SELECT id_opcion, texto_destino, recurso_destino_url, es_correcto FROM emparejar_opciones WHERE id_actividad = 4;

SELECT 'RECURSOS MULTIMEDIA:' as Info;
SELECT id_recurso, tipo_recurso, nombre_archivo FROM recursos WHERE id_unidad = 1;

-- =====================================================
-- INSTRUCCIONES DE USO:
-- =====================================================
/*
1. Ejecuta este script en tu base de datos MySQL 'proyecto_kiwchaa'
2. Verifica que los datos se insertaron correctamente con las consultas al final
3. Asegúrate de tener las imágenes en las rutas especificadas:
   - /imagenes/tierra.jpg
   - /imagenes/agua.jpg
   - /imagenes/sol.jpg
   - /imagenes/luna.jpg
   - /imagenes/montaña.jpg
   - /imagenes/arbol.jpg
4. Si no tienes las imágenes, la actividad 3 mostrará texto alternativo
5. Prueba las actividades desde la aplicación

NOTAS:
- La Actividad 1 usará las preguntas de la tabla 'evaluaciones'
- La Actividad 2 usará los datos de 'drag_drop_items' y 'emparejar_opciones'
- La Actividad 3 usará los pares palabra-imagen de 'emparejar_opciones' con id_actividad = 4
- Los distractores son palabras sin imagen (recurso_destino_url = NULL)
*/
