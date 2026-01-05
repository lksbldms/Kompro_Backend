#!/bin/zsh
BASE="src/main/kotlin/info/kompro"

# Liste deiner tatsächlichen Kompro-Entities
ENTITIES=(
  "Account" 
  "User" 
  "AuthToken" 
  "RatingPattern" 
  "RatingChoice" 
  "CompetenceArea" 
  "SharedTemplate" 
  "Competence" 
  "Item" 
  "RatingSession" 
  "Rating"
)

# Ordner sicherstellen
mkdir -p "$BASE/service" "$BASE/controller"

for ENTITY in "${ENTITIES[@]}"; do
  # URL-Format (CamelCase -> kebab-case)
  LOWER=$(echo "$ENTITY" | sed 's/\([a-z0-9]\)\([A-Z]\)/\1-\2/g' | tr '[:upper:]' '[:lower:]')
  
  # 1. Service erstellen (Standard CRUD)
  cat <<EOF > "$BASE/service/${ENTITY}Service.kt"
package info.kompro.service

import info.kompro.entity.${ENTITY}
import info.kompro.repository.${ENTITY}Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ${ENTITY}Service(private val repository: ${ENTITY}Repository) {
    fun findAll(): List<${ENTITY}> = repository.findAll()
    fun findById(id: UUID): ${ENTITY}? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: ${ENTITY}): ${ENTITY} = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
EOF

  # 2. Controller erstellen (REST API)
  cat <<EOF > "$BASE/controller/${ENTITY}Controller.kt"
package info.kompro.controller

import info.kompro.entity.${ENTITY}
import info.kompro.service.${ENTITY}Service
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/${LOWER}s")
class ${ENTITY}Controller(private val service: ${ENTITY}Service) {

    @GetMapping
    fun getAll(): List<${ENTITY}> = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ${ENTITY}? = service.findById(id)

    @PostMapping
    fun create(@RequestBody entity: ${ENTITY}): ${ENTITY} = service.save(entity)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
EOF
done

echo "✅ Services und Controller für alle Kompro-Entities erfolgreich erstellt!"