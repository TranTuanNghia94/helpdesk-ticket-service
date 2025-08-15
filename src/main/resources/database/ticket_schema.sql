-- ====================================
-- HELPDESK SYSTEM - TICKET MANAGEMENT SCHEMA DDL
-- ====================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";
-- ====================================
-- CATEGORIES
-- ====================================

CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description TEXT,
    parent_category_id UUID REFERENCES categories(id),
    icon VARCHAR(100),
    color VARCHAR(7),
    is_active BOOLEAN DEFAULT TRUE,
    sort_order INTEGER DEFAULT 0,
    auto_assignment_rules JSONB,
    escalation_rules JSONB,
    sla_id UUID,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id),
    updated_by UUID REFERENCES users(id),
    UNIQUE(organization_id, code)
);

CREATE INDEX idx_categories_organization ON categories(organization_id);
CREATE INDEX idx_categories_parent ON categories(parent_category_id);
CREATE INDEX idx_categories_active ON categories(is_active);

-- ====================================
-- PRIORITIES
-- ====================================

CREATE TABLE priorities (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description TEXT,
    level INTEGER NOT NULL,
    color VARCHAR(7),
    icon VARCHAR(100),
    response_time_hours INTEGER,
    resolution_time_hours INTEGER,
    auto_escalate BOOLEAN DEFAULT FALSE,
    escalation_time_hours INTEGER,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id),
    updated_by UUID REFERENCES users(id),
    UNIQUE(organization_id, code),
    UNIQUE(organization_id, level)
);

CREATE INDEX idx_priorities_organization ON priorities(organization_id);
CREATE INDEX idx_priorities_level ON priorities(level);
CREATE INDEX idx_priorities_active ON priorities(is_active);

-- ====================================
-- TICKET STATUSES
-- ====================================

CREATE TABLE ticket_statuses (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    description TEXT,
    status_type VARCHAR(20) NOT NULL CHECK (status_type IN ('OPEN', 'IN_PROGRESS', 'PENDING', 'RESOLVED', 'CLOSED', 'CANCELLED')),
    color VARCHAR(7),
    icon VARCHAR(100),
    is_initial BOOLEAN DEFAULT FALSE,
    is_final BOOLEAN DEFAULT FALSE,
    auto_close_hours INTEGER,
    sort_order INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    workflow_rules JSONB,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id),
    updated_by UUID REFERENCES users(id),
    UNIQUE(organization_id, code)
);

CREATE INDEX idx_ticket_statuses_organization ON ticket_statuses(organization_id);
CREATE INDEX idx_ticket_statuses_type ON ticket_statuses(status_type);
CREATE INDEX idx_ticket_statuses_active ON ticket_statuses(is_active);

-- ====================================
-- TICKETS
-- ====================================

CREATE TABLE tickets (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    ticket_number VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(500) NOT NULL,
    description TEXT,
    
    -- Requester information
    requester_id UUID NOT NULL REFERENCES users(id),
    requester_email VARCHAR(255),
    requester_phone VARCHAR(50),
    
    -- Assignment
    assigned_to_id UUID REFERENCES users(id),
    assigned_team_id UUID REFERENCES teams(id),
    assigned_at TIMESTAMPTZ,
    assigned_by_id UUID REFERENCES users(id),
    
    -- Classification
    category_id UUID REFERENCES categories(id),
    subcategory_id UUID REFERENCES categories(id),
    priority_id UUID NOT NULL REFERENCES priorities(id),
    status_id UUID NOT NULL REFERENCES ticket_statuses(id),
    
    -- Ticket type and source
    ticket_type VARCHAR(50) DEFAULT 'INCIDENT' CHECK (ticket_type IN ('INCIDENT', 'SERVICE_REQUEST', 'PROBLEM', 'CHANGE')),
    source VARCHAR(50) DEFAULT 'EMAIL' CHECK (source IN ('EMAIL', 'WEB', 'PHONE', 'CHAT', 'API', 'SYSTEM')),
    
    -- Asset and configuration
    affected_asset_id UUID,
    configuration_items JSONB,
    
    -- Business impact
    urgency VARCHAR(20) DEFAULT 'MEDIUM' CHECK (urgency IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    impact VARCHAR(20) DEFAULT 'MEDIUM' CHECK (impact IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    business_justification TEXT,
    
    -- SLA and timing
    sla_id UUID,
    due_date TIMESTAMPTZ,
    response_due_date TIMESTAMPTZ,
    resolution_due_date TIMESTAMPTZ,
    first_response_at TIMESTAMPTZ,
    resolved_at TIMESTAMPTZ,
    closed_at TIMESTAMPTZ,
    
    -- Resolution
    resolution TEXT,
    resolution_category VARCHAR(100),
    root_cause TEXT,
    
    -- Metrics
    time_spent_minutes INTEGER DEFAULT 0,
    satisfaction_rating INTEGER CHECK (satisfaction_rating BETWEEN 1 AND 5),
    satisfaction_feedback TEXT,
    
    -- Flags and settings
    is_escalated BOOLEAN DEFAULT FALSE,
    escalated_at TIMESTAMPTZ,
    escalated_by_id UUID REFERENCES users(id),
    escalation_reason TEXT,
    
    is_overdue BOOLEAN DEFAULT FALSE,
    is_reopened BOOLEAN DEFAULT FALSE,
    reopen_count INTEGER DEFAULT 0,
    
    is_parent BOOLEAN DEFAULT FALSE,
    parent_ticket_id UUID REFERENCES tickets(id),
    
    -- Custom fields and metadata
    custom_fields JSONB,
    tags JSONB,
    
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id),
    updated_by UUID REFERENCES users(id)
);

CREATE INDEX idx_tickets_organization ON tickets(organization_id);
CREATE INDEX idx_tickets_number ON tickets(ticket_number);
CREATE INDEX idx_tickets_requester ON tickets(requester_id);
CREATE INDEX idx_tickets_assigned_to ON tickets(assigned_to_id);
CREATE INDEX idx_tickets_assigned_team ON tickets(assigned_team_id);
CREATE INDEX idx_tickets_category ON tickets(category_id);
CREATE INDEX idx_tickets_priority ON tickets(priority_id);
CREATE INDEX idx_tickets_status ON tickets(status_id);
CREATE INDEX idx_tickets_type ON tickets(ticket_type);
CREATE INDEX idx_tickets_source ON tickets(source);
CREATE INDEX idx_tickets_due_date ON tickets(due_date);
CREATE INDEX idx_tickets_created ON tickets(created_at);
CREATE INDEX idx_tickets_updated ON tickets(updated_at);
CREATE INDEX idx_tickets_parent ON tickets(parent_ticket_id);
CREATE INDEX idx_tickets_asset ON tickets(affected_asset_id);
CREATE INDEX idx_tickets_escalated ON tickets(is_escalated);
CREATE INDEX idx_tickets_overdue ON tickets(is_overdue);

-- Full text search index
CREATE INDEX idx_tickets_search ON tickets USING gin(to_tsvector('english', title || ' ' || COALESCE(description, '')));

-- ====================================
-- TICKET COMMENTS
-- ====================================

CREATE TABLE ticket_comments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    ticket_id UUID NOT NULL REFERENCES tickets(id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES users(id),
    content TEXT NOT NULL,
    is_internal BOOLEAN DEFAULT FALSE,
    is_system_generated BOOLEAN DEFAULT FALSE,
    comment_type VARCHAR(50) DEFAULT 'COMMENT' CHECK (comment_type IN ('COMMENT', 'RESOLUTION', 'WORK_LOG', 'STATUS_CHANGE', 'ASSIGNMENT')),
    time_spent_minutes INTEGER DEFAULT 0,
    attachments JSONB,
    mentions JSONB,
    edited_at TIMESTAMPTZ,
    edited_by_id UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ticket_comments_ticket ON ticket_comments(ticket_id);
CREATE INDEX idx_ticket_comments_author ON ticket_comments(author_id);
CREATE INDEX idx_ticket_comments_type ON ticket_comments(comment_type);
CREATE INDEX idx_ticket_comments_created ON ticket_comments(created_at);
CREATE INDEX idx_ticket_comments_internal ON ticket_comments(is_internal);

-- ====================================
-- TICKET ATTACHMENTS
-- ====================================

CREATE TABLE ticket_attachments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    ticket_id UUID NOT NULL REFERENCES tickets(id) ON DELETE CASCADE,
    comment_id UUID REFERENCES ticket_comments(id) ON DELETE CASCADE,
    filename VARCHAR(255) NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    checksum VARCHAR(255),
    is_image BOOLEAN DEFAULT FALSE,
    thumbnail_path VARCHAR(500),
    uploaded_by_id UUID NOT NULL REFERENCES users(id),
    uploaded_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ticket_attachments_ticket ON ticket_attachments(ticket_id);
CREATE INDEX idx_ticket_attachments_comment ON ticket_attachments(comment_id);
CREATE INDEX idx_ticket_attachments_uploader ON ticket_attachments(uploaded_by_id);

-- ====================================
-- TICKET WATCHERS
-- ====================================

CREATE TABLE ticket_watchers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    ticket_id UUID NOT NULL REFERENCES tickets(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    added_by_id UUID REFERENCES users(id),
    added_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notification_preferences JSONB,
    UNIQUE(ticket_id, user_id)
);

CREATE INDEX idx_ticket_watchers_ticket ON ticket_watchers(ticket_id);
CREATE INDEX idx_ticket_watchers_user ON ticket_watchers(user_id);

-- ====================================
-- TICKET HISTORY
-- ====================================

CREATE TABLE ticket_history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    ticket_id UUID NOT NULL REFERENCES tickets(id) ON DELETE CASCADE,
    changed_by_id UUID REFERENCES users(id),
    change_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(100),
    old_value TEXT,
    new_value TEXT,
    description TEXT,
    is_system_change BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ticket_history_ticket ON ticket_history(ticket_id);
CREATE INDEX idx_ticket_history_changed_by ON ticket_history(changed_by_id);
CREATE INDEX idx_ticket_history_type ON ticket_history(change_type);
CREATE INDEX idx_ticket_history_created ON ticket_history(created_at);

-- ====================================
-- TICKET RELATIONSHIPS
-- ====================================

CREATE TABLE ticket_relationships (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    source_ticket_id UUID NOT NULL REFERENCES tickets(id) ON DELETE CASCADE,
    target_ticket_id UUID NOT NULL REFERENCES tickets(id) ON DELETE CASCADE,
    relationship_type VARCHAR(50) NOT NULL CHECK (relationship_type IN ('DUPLICATE', 'RELATED', 'BLOCKS', 'BLOCKED_BY', 'CAUSED_BY', 'CHILD_OF', 'PARENT_OF')),
    description TEXT,
    created_by_id UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(source_ticket_id, target_ticket_id, relationship_type)
);

CREATE INDEX idx_ticket_relationships_source ON ticket_relationships(source_ticket_id);
CREATE INDEX idx_ticket_relationships_target ON ticket_relationships(target_ticket_id);
CREATE INDEX idx_ticket_relationships_type ON ticket_relationships(relationship_type);

-- ====================================
-- TICKET TEMPLATES
-- ====================================

CREATE TABLE ticket_templates (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category_id UUID REFERENCES categories(id),
    priority_id UUID REFERENCES priorities(id),
    title_template VARCHAR(500),
    description_template TEXT,
    assigned_team_id UUID REFERENCES teams(id),
    custom_fields_template JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    usage_count INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID REFERENCES users(id),
    updated_by UUID REFERENCES users(id)
);

CREATE INDEX idx_ticket_templates_organization ON ticket_templates(organization_id);
CREATE INDEX idx_ticket_templates_category ON ticket_templates(category_id);
CREATE INDEX idx_ticket_templates_active ON ticket_templates(is_active);

-- ====================================
-- UPDATE TRIGGERS
-- ====================================

CREATE TRIGGER update_categories_updated_at BEFORE UPDATE ON categories FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_priorities_updated_at BEFORE UPDATE ON priorities FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_ticket_statuses_updated_at BEFORE UPDATE ON ticket_statuses FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_tickets_updated_at BEFORE UPDATE ON tickets FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_ticket_templates_updated_at BEFORE UPDATE ON ticket_templates FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ====================================
-- FUNCTIONS AND TRIGGERS FOR TICKET AUTOMATION
-- ====================================

-- Function to generate ticket number
CREATE OR REPLACE FUNCTION generate_ticket_number()
RETURNS TRIGGER AS $$
DECLARE
    prefix TEXT := 'TKT';
    sequence_num INTEGER;
BEGIN
    -- Get next sequence number for the organization
    SELECT COALESCE(MAX(CAST(SUBSTRING(ticket_number FROM '[0-9]+$') AS INTEGER)), 0) + 1
    INTO sequence_num
    FROM tickets
    WHERE organization_id = NEW.organization_id;
    
    NEW.ticket_number := prefix || '-' || LPAD(sequence_num::TEXT, 6, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to auto-generate ticket number
CREATE TRIGGER generate_ticket_number_trigger
    BEFORE INSERT ON tickets
    FOR EACH ROW
    WHEN (NEW.ticket_number IS NULL)
    EXECUTE FUNCTION generate_ticket_number();

-- Function to create ticket history entries
CREATE OR REPLACE FUNCTION create_ticket_history()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO ticket_history (ticket_id, changed_by_id, change_type, description, is_system_change)
        VALUES (NEW.id, NEW.created_by, 'CREATED', 'Ticket created', false);
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        -- Track status changes
        IF OLD.status_id != NEW.status_id THEN
            INSERT INTO ticket_history (ticket_id, changed_by_id, change_type, field_name, old_value, new_value)
            SELECT NEW.id, NEW.updated_by, 'STATUS_CHANGED', 'status_id', 
                   OLD_STATUS.name, NEW_STATUS.name
            FROM ticket_statuses OLD_STATUS, ticket_statuses NEW_STATUS
            WHERE OLD_STATUS.id = OLD.status_id AND NEW_STATUS.id = NEW.status_id;
        END IF;
        
        -- Track assignment changes
        IF OLD.assigned_to_id IS DISTINCT FROM NEW.assigned_to_id THEN
            INSERT INTO ticket_history (ticket_id, changed_by_id, change_type, field_name, old_value, new_value)
            VALUES (NEW.id, NEW.updated_by, 'ASSIGNED', 'assigned_to_id', 
                   OLD.assigned_to_id::TEXT, NEW.assigned_to_id::TEXT);
        END IF;
        
        -- Track priority changes
        IF OLD.priority_id != NEW.priority_id THEN
            INSERT INTO ticket_history (ticket_id, changed_by_id, change_type, field_name, old_value, new_value)
            SELECT NEW.id, NEW.updated_by, 'PRIORITY_CHANGED', 'priority_id',
                   OLD_PRIORITY.name, NEW_PRIORITY.name
            FROM priorities OLD_PRIORITY, priorities NEW_PRIORITY
            WHERE OLD_PRIORITY.id = OLD.priority_id AND NEW_PRIORITY.id = NEW.priority_id;
        END IF;
        
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Trigger for ticket history
CREATE TRIGGER ticket_history_trigger
    AFTER INSERT OR UPDATE ON tickets
    FOR EACH ROW
    EXECUTE FUNCTION create_ticket_history();

-- ====================================
-- INITIAL DATA
-- ====================================

-- Insert default priorities
INSERT INTO priorities (organization_id, name, code, level, color, response_time_hours, resolution_time_hours) VALUES
('00000000-0000-0000-0000-000000000000', 'Low', 'LOW', 1, '#28a745', 72, 168),
('00000000-0000-0000-0000-000000000000', 'Medium', 'MEDIUM', 2, '#ffc107', 24, 72),
('00000000-0000-0000-0000-000000000000', 'High', 'HIGH', 3, '#fd7e14', 8, 24),
('00000000-0000-0000-0000-000000000000', 'Critical', 'CRITICAL', 4, '#dc3545', 2, 8);

-- Insert default ticket statuses
INSERT INTO ticket_statuses (organization_id, name, code, status_type, color, is_initial, is_final, sort_order) VALUES
('00000000-0000-0000-0000-000000000000', 'New', 'NEW', 'OPEN', '#007bff', true, false, 1),
('00000000-0000-0000-0000-000000000000', 'Open', 'OPEN', 'OPEN', '#28a745', false, false, 2),
('00000000-0000-0000-0000-000000000000', 'In Progress', 'IN_PROGRESS', 'IN_PROGRESS', '#ffc107', false, false, 3),
('00000000-0000-0000-0000-000000000000', 'Pending Customer', 'PENDING_CUSTOMER', 'PENDING', '#6c757d', false, false, 4),
('00000000-0000-0000-0000-000000000000', 'Pending Vendor', 'PENDING_VENDOR', 'PENDING', '#6f42c1', false, false, 5),
('00000000-0000-0000-0000-000000000000', 'Resolved', 'RESOLVED', 'RESOLVED', '#20c997', false, false, 6),
('00000000-0000-0000-0000-000000000000', 'Closed', 'CLOSED', 'CLOSED', '#6c757d', false, true, 7),
('00000000-0000-0000-0000-000000000000', 'Cancelled', 'CANCELLED', 'CANCELLED', '#dc3545', false, true, 8);

-- Insert default categories
INSERT INTO categories (organization_id, name, code, description) VALUES
('00000000-0000-0000-0000-000000000000', 'Hardware', 'HARDWARE', 'Hardware related issues'),
('00000000-0000-0000-0000-000000000000', 'Software', 'SOFTWARE', 'Software related issues'),
('00000000-0000-0000-0000-000000000000', 'Network', 'NETWORK', 'Network connectivity issues'),
('00000000-0000-0000-0000-000000000000', 'Account', 'ACCOUNT', 'User account and access issues'),
('00000000-0000-0000-0000-000000000000', 'Email', 'EMAIL', 'Email related issues'),
('00000000-0000-0000-0000-000000000000', 'General Inquiry', 'GENERAL', 'General questions and requests'); 