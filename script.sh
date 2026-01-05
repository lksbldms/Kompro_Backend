#!/bin/zsh
BASE="src/main/kotlin/info/kompro"

# Die 8 Repositories aus deinem Chat-Verlauf
declare -A REPOS
REPOS=(
  ["UserRepository"]="User"
  ["CompetenceAreaRepository"]="CompetenceArea"
  ["RatingSessionRepository"]="RatingSession"
  ["RatingRepository"]="Rating"
  ["AuthTokenRepository"]="AuthToken"
  ["CompetenceRepository"]="Competence"
  ["ItemRepository"]="Item"
  ["RatingPatternRepository"]="RatingPattern"
)

for REPO in "${(@k)REPOS}"; do
  ENTITY="${REPOS[$REPO]}"
  # Pfad-Formatierung (z.B. CompetenceArea -> competence-areas)
  LOWER=$(echo "$ENTITY" | sed 's/\([a-z0-9]\)\([A-Z]\)/\1-\2/g' | tr '[:upper:]' '[:lower:]')
  
  # Service erstellen
  cat <<EOF > "$BASE/service/${ENTITY}Service.kt"
package info.kompro.service

import info.kompro.entity.${ENTITY}
import info.kompro.repository.${REPO}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ${ENTITY}Service(private val repository: ${REPO}) {
    fun findAll(): List<${ENTITY}> = repository.findAll()
    fun findById(id: UUID): ${ENTITY}? = repository.findById(id).orElse(null)
    
    @Transactional
    fun save(entity: ${ENTITY}): ${ENTITY} = repository.save(entity)
    
    @Transactional
    fun deleteById(id: UUID) = repository.deleteById(id)
}
EOF

  # Controller erstellen
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
    fun getAll() = service.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @PostMapping
    fun create(@RequestBody entity: ${ENTITY}) = service.save(entity)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) = service.deleteById(id)
}
EOF
done

echo "✅ Services und Controller für deine 8 Repositories wurden erstellt!"